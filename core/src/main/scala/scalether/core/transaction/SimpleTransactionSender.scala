package scalether.core.transaction

import cats.MonadError
import scalether.core.{Ethereum, TransactionSender}
import scalether.core.request.Transaction

import scala.language.higherKinds

class SimpleTransactionSender[F[_]](ethereum: Ethereum[F], from: String, gas: BigInt, gasPrice: BigInt)(implicit me: MonadError[F, Throwable])
  extends TransactionSender[F] {

  def call(transaction: Transaction) =
    ethereum.ethCall(transaction)

  def sendTransaction(transaction: Transaction) =
    ethereum.ethSendTransaction(transaction.copy(
      from = Some(from),
      gas = Some(gas),
      gasPrice = Some(gasPrice)
    ))
}
