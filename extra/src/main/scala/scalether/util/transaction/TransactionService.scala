package scalether.util.transaction

import cats.implicits._
import cats.Functor
import scalether.core.{Ethereum, TransactionReceipt}
import scalether.util.timer.Poller

import scala.language.higherKinds

class TransactionService[F[_]](ethereum: Ethereum[F])(implicit f: Functor[F], pollService: Poller[F]) {
  def waitForTransaction(txHash: String): F[TransactionReceipt] = {
    pollService.poll(1000)(pollForTransaction(txHash))
  }

  private def pollForTransaction(txHash: String): F[Option[TransactionReceipt]] =
    ethereum.ethGetTransactionReceipt(txHash).map(r => Option(r))
}
