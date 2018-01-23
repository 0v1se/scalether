package scalether.extra.log

import java.math.BigInteger

import cats.Monad
import cats.implicits._
import scalether.core.Ethereum
import scalether.domain.response.Log
import scalether.util.Hex

import scala.language.higherKinds

class LogListenService[F[_]](ethereum: Ethereum[F],
                             confidence: Int,
                             listener: LogListener[F],
                             knownBlockState: State[BigInteger, F])
                            (implicit m: Monad[F]) {

  def check(): F[List[Log]] = for {
    blockNumber <- ethereum.ethBlockNumber()
    savedBlockNumber <- knownBlockState.get
    logs <- fetchLogs(blockNumber, savedBlockNumber)
  } yield logs

  private def fetchLogs(blockNumber: BigInteger, savedBlockNumber: Option[BigInteger]): F[List[Log]] = {
    if (savedBlockNumber.contains(blockNumber)) {
      m.pure(Nil)
    } else {
      val fromBlock = savedBlockNumber match {
        case Some(value) => value.subtract(BigInteger.valueOf(confidence))
        case None => BigInteger.ZERO
      }
      for {
        filter <- listener.createFilter(Hex.prefixed(fromBlock), Hex.prefixed(blockNumber))
        logs <- ethereum.ethGetLogs(filter)
        _ <- notifyListeners(blockNumber, logs)
        _ <- knownBlockState.set(blockNumber)
      } yield logs
    }
  }

  private def notifyListeners(blockNumber: BigInteger, logs: List[Log]): F[Unit] =
    logs.foldRight(m.unit)((log, monad) => monad.flatMap(_ => notifyListener(blockNumber, log)))

  private def notifyListener(blockNumber: BigInteger, log: Log): F[Unit] =
    listener.onLog(log, blockNumber.subtract(log.blockNumber).compareTo(BigInteger.valueOf(confidence)) >= 0)
}
