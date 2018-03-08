package scalether.listener.transfer

import java.math.BigInteger

import cats.MonadError
import cats.implicits._
import org.slf4j.{Logger, LoggerFactory}
import scalether.core.{Ethereum, Parity}
import scalether.domain.response.parity.Trace
import scalether.listener.common.{Notify, State}

import scala.language.higherKinds

class TransferListenService[F[_]](ethereum: Ethereum[F], parity: Parity[F], confidence: Int, listener: TransferListener[F], state: State[BigInteger, F])
                                 (implicit m: MonadError[F, Throwable]) {

  val logger: Logger = LoggerFactory.getLogger(getClass)

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

  private def fetchAndNotify(blockNumber: BigInteger, saved: Option[BigInteger]): F[Unit] = {
    val from = saved.getOrElse(blockNumber.subtract(BigInteger.ONE))
    val start = from.subtract(BigInteger.valueOf(confidence - 1))
    val numbers = blockNumbers(start, blockNumber)
    Notify.every(numbers)(fetchAndNotify(blockNumber))
  }

  private def blockNumbers(from: BigInteger, to: BigInteger): List[BigInteger] = {
    if (from.compareTo(to) >= 0)
      Nil
    else if (to.subtract(from).compareTo(BigInteger.TEN.pow(3)) >= 0)
      throw new IllegalArgumentException("unable to process more than 1000 blocks")
    else if (from.compareTo(BigInteger.ZERO) < 0)
      blockNumbers(from.add(BigInteger.ONE), to)
    else
      from.add(BigInteger.ONE) :: blockNumbers(from.add(BigInteger.ONE), to)
  }

  def fetchAndNotify(latestBlock: BigInteger)(block: BigInteger): F[Unit] =
    parity.traceBlock(block).flatMap(notifyListenerAboutTraces(latestBlock, block))

  private def notifyListenerAboutTraces(latestBlock: BigInteger, block: BigInteger)(traces: List[Trace]): F[Unit] = {
    Notify.every(traces)(notifyListener(latestBlock))
  }

  private def notifyListener(latestBlock: BigInteger)(trace: Trace): F[Unit] = {
    val confirmations = latestBlock.subtract(trace.blockNumber).intValue() + 1
    if (trace.action == null
      || trace.action.value == BigInteger.ZERO
      || trace.error != null
      || trace.`type` == "reward"
      || trace.action.to == null
      || trace.action.from == null
      || trace.transactionHash == null
    ) {
      m.unit
    } else {
      listener.onTransfer(Transfer(trace.action.from, trace.action.to, trace.action.value, trace.transactionHash.toString), confirmations, confirmations >= confidence)
    }
  }
}
