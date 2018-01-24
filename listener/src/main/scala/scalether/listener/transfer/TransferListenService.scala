package scalether.listener.transfer

import java.math.BigInteger

import cats.Monad
import cats.implicits._
import scalether.core.{Ethereum, Parity}
import scalether.domain.response.parity.Trace
import scalether.listener.common.{Notify, State}

import scala.language.higherKinds

class TransferListenService[F[_]](ethereum: Ethereum[F], parity: Parity[F], listener: TransferListener[F], state: State[BigInteger, F])
                                 (implicit m: Monad[F]) {

  def check(blockNumber: BigInteger): F[Unit] =
    if (listener.enabled) {
      checkInternal(blockNumber)
    } else {
      m.unit
    }

  def checkInternal(blockNumber: BigInteger): F[Unit] = for {
    saved <- state.get
    _ <- fetchAndNotify(blockNumber, saved)
    _ <- state.set(blockNumber)
  } yield ()

  private def fetchAndNotify(blockNumber: BigInteger, saved: Option[BigInteger]): F[Unit] = saved match {
    case None => m.pure(Nil)
    case Some(savedBlockNumber) => Notify.every(blockNumbers(savedBlockNumber, blockNumber))(fetchAndNotify)
  }

  private def blockNumbers(from: BigInteger, to: BigInteger): List[BigInteger] = {
    if (from.compareTo(to) >= 0)
      Nil
    else
      from.add(BigInteger.ONE) :: blockNumbers(from.add(BigInteger.ONE), to)
  }

  private def fetchAndNotify(block: BigInteger): F[Unit] = for {
    block <- ethereum.ethGetBlockByNumber(block)
    _ <- checkTransactions(block.transactions)
  } yield ()

  private def checkTransactions(hashes: List[String]): F[Unit] =
    Notify.every(hashes)(checkTransaction)

  private def checkTransaction(hash: String): F[Unit] = {
    parity.traceTransaction(hash)
        .flatMap(notifyListener)
  }

  private def notifyListener(traces: List[Trace]): F[Unit] =
    Notify.every(traces)(notifyListener)

  private def notifyListener(trace: Trace): F[Unit] =
    if (trace.action.value != BigInteger.ZERO) {
      listener.onTransfer(Transfer(trace.action.from, trace.action.to, trace.action.value, trace.transactionHash.toString), 0, confirmed = true)
    } else {
      m.unit
    }
}
