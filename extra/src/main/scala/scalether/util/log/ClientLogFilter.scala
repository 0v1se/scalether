package scalether.util.log

import java.math.BigInteger

import cats.Monad
import cats.implicits._
import scalether.core.request.LogFilter
import scalether.core.{Ethereum, Log}
import scalether.util.Hex

import scala.language.higherKinds

class ClientLogFilter[F[_]](ethereum: Ethereum[F], filter: LogFilter, state: LogFilterState[F])(implicit m: Monad[F]) {
  def getChanges: F[List[Log]] = {
    def block(num: BigInteger) = Hex.prefixed(num.toByteArray)

    for {
      current <- ethereum.ethBlockNumber()
      latest <- state.getBlock
      logs <- ethereum.ethGetLogs(filter.copy(fromBlock = block(latest), toBlock = block(current)))
      _ <- state.setBlock(current)
    } yield logs
  }
}


