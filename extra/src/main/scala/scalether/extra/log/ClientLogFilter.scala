package scalether.extra.log

import java.math.BigInteger

import cats.Monad
import cats.implicits._
import scalether.core.Ethereum
import scalether.domain.request.LogFilter
import scalether.domain.response.Log

import scala.language.higherKinds

/**
  * вначале спрашивать blockNumber, потом сохранять его, и, если поменялся, то запрашивать логи
  */
class ClientLogFilter[F[_]](ethereum: Ethereum[F], filter: LogFilter, state: LogFilterState[F])(implicit m: Monad[F]) {
  def getChanges(blockNumber: BigInteger): F[List[Log]] = {
    def block(num: BigInteger) = s"0x${num.toString(16)}"

    for {
      latest <- state.getBlock
      logs <- ethereum.ethGetLogs(filter.copy(fromBlock = block(latest), toBlock = block(blockNumber)))
      _ <- state.setBlock(blockNumber)
    } yield logs
  }
}


