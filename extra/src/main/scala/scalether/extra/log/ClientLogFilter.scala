package scalether.extra.log

import java.math.BigInteger

import cats.Monad
import cats.implicits._
import scalether.core.Ethereum
import scalether.domain.request.LogFilter
import scalether.domain.response.Log
import scalether.util.Hex

import scala.language.higherKinds

class ClientLogFilter[F[_]](ethereum: Ethereum[F], filter: LogFilter, state: LogFilterState[F])(implicit m: Monad[F]) {
  def getChanges(blockNumber: BigInteger): F[List[Log]] = {
    for {
      latest <- state.getBlock
      logs <- if (latest == blockNumber) m.pure(Nil) else ethereum.ethGetLogs(filter.copy(fromBlock = Hex.prefixed(latest), toBlock = Hex.prefixed(blockNumber)))
      _ <- state.setBlock(blockNumber)
    } yield logs
  }
}


