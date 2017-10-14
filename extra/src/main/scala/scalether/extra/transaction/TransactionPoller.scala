package scalether.extra.transaction

import cats.Monad
import cats.implicits._
import scalether.core.{Ethereum, TransactionReceipt}
import scalether.extra.timer.Poller

import scala.language.higherKinds

class TransactionPoller[F[_]](val ethereum: Ethereum[F])
                             (implicit f: Monad[F], poller: Poller[F]) {

  def waitForTransaction(txHash: F[String]): F[TransactionReceipt] = for {
    hash <- txHash
    result <- poller.poll(1000)(pollForTransaction(hash))
  } yield result

  private def pollForTransaction(txHash: String): F[Option[TransactionReceipt]] =
    ethereum.ethGetTransactionReceipt(txHash).map(r => Option(r))
}
