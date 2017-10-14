package scalether.core.transaction

import java.math.BigInteger

import scalether.core.request.Transaction
import scalether.core.{Ethereum, TransactionSender}
import scalether.domain.Address

import scala.language.higherKinds

class SimpleTransactionSender[F[_]](ethereum: Ethereum[F], from: Address, gas: BigInteger, gasPrice: BigInteger)
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
