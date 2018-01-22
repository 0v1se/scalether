package scalether.extra.log

import java.math.BigInteger

import cats.Monad
import cats.implicits._
import scalether.core.Ethereum
import scalether.domain.response.Log
import scalether.util.Hex

import scala.language.higherKinds

class LogListenerService[F[_]](ethereum: Ethereum[F],
                               confidence: Int,
                               listener: LogListener[F],
                               knownBlockState: State[BigInteger, F])
                              (implicit m: Monad[F]) {

  def check(): F[Unit] = for {
    blockNumber <- ethereum.ethBlockNumber()
    savedBlockNumber <- knownBlockState.get
  } yield {
    if (savedBlockNumber.contains(blockNumber)) {
      m.unit
    } else {
      val fromBlock = savedBlockNumber match {
        case Some(value) => value.subtract(BigInteger.valueOf(confidence))
        case None => BigInteger.ZERO
      }
      listener.createFilter(Hex.prefixed(fromBlock), Hex.prefixed(blockNumber))
        .flatMap(ethereum.ethGetLogs)
        .flatMap(logs => notifyListeners(blockNumber, logs))
        .flatMap(_ => knownBlockState.set(blockNumber))
    }
  }

  private def notifyListeners(blockNumber: BigInteger, logs: List[Log]): F[Unit] =
    logs.foldRight(m.unit)((log, monad) => monad.flatMap(_ => notifyListener(blockNumber, log)))

  private def notifyListener(blockNumber: BigInteger, log: Log): F[Unit] =
    listener.onLog(log, blockNumber.subtract(log.blockNumber).compareTo(BigInteger.valueOf(confidence)) >= 0)
}
