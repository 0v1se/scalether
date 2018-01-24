package scalether.listener.transfer

import java.math.BigInteger

import cats.Monad
import cats.implicits._
import scalether.core.{Ethereum, Parity}
import scalether.domain.Address
import scalether.domain.response.parity.Trace
import scalether.listener.common.{Notify, State}

import scala.language.higherKinds

class TransferListenService[F[_]](ethereum: Ethereum[F], parity: Parity[F], confidence: Int, listener: TransferListener[F], state: State[BigInteger, F])
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
    case Some(savedBlockNumber) => Notify.every(blockNumbers(savedBlockNumber, blockNumber))(fetchAndNotify(blockNumber))
  }

  private def blockNumbers(from: BigInteger, to: BigInteger): List[BigInteger] = {
    if (from.compareTo(to) >= 0)
      Nil
    else
      from.add(BigInteger.ONE) :: blockNumbers(from.add(BigInteger.ONE), to)
  }

  def fetchAndNotify(latestBlock: BigInteger)(block: BigInteger): F[Unit] =
    parity.traceBlock(block).flatMap(notifyListenerAboutTraces(latestBlock))

  private def notifyListenerAboutTraces(latestBlock: BigInteger)(traces: List[Trace]): F[Unit] =
    Notify.every(traces)(notifyListener(latestBlock))

  private def notifyListener(latestBlock: BigInteger)(trace: Trace): F[Unit] = {
    val confirmations = latestBlock.subtract(trace.blockNumber).intValue() + 1
    if (trace.`type` == "reward") {
      listener.onTransfer(Transfer(Address.apply(new Array[Byte](20)), trace.action.author, trace.action.value, null), confirmations, confirmations >= confidence)
    } else if (trace.action.value != BigInteger.ZERO) {
      listener.onTransfer(Transfer(trace.action.from, trace.action.to, trace.action.value, trace.transactionHash.toString), confirmations, confirmations >= confidence)
    } else {
      m.unit
    }
  }
}
