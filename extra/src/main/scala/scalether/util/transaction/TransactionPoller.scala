package scalether.util.transaction

import cats.implicits._
import cats.Functor
import scalether.core.{Ethereum, TransactionReceipt}
import scalether.util.timer.Poller

import scala.language.higherKinds

class TransactionPoller[F[_]](val ethereum: Ethereum[F])(implicit f: Functor[F], poller: Poller[F]) {
  def waitForTransaction(txHash: String): F[TransactionReceipt] = {
    poller.poll(1000)(pollForTransaction(txHash))
  }

  private def pollForTransaction(txHash: String): F[Option[TransactionReceipt]] =
    ethereum.ethGetTransactionReceipt(txHash).map(r => Option(r))
}
