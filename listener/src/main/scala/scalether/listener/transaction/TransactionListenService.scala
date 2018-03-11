package scalether.listener.transaction

import java.math.BigInteger

import cats.MonadError
import cats.implicits._
import org.slf4j.{Logger, LoggerFactory}
import scalether.core.Ethereum
import scalether.domain.response.Block
import scalether.listener.common.{Notify, State}

import scala.language.higherKinds

class TransactionListenService[F[_]](ethereum: Ethereum[F], confidence: Int, listener: TransactionListener[F], state: State[BigInteger, F])
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
    ethereum.ethGetBlockByNumber(block).flatMap(notifyListenerAboutTransactions(latestBlock, block))

  private def notifyListenerAboutTransactions(latestBlock: BigInteger, block: BigInteger)(fetched: Block): F[Unit] = {
    Notify.every(fetched.transactions)(notifyListener(latestBlock, fetched.number))
  }

  private def notifyListener(latestBlock: BigInteger, block: BigInteger)(txHash: String): F[Unit] = {
    val confirmations = latestBlock.subtract(block).intValue() + 1
    listener.onTransaction(txHash, confirmations, confirmations >= confidence)
  }
}
