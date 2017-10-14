package scalether.extra.transaction

import java.math.BigInteger

import scalether.core.Ethereum
import scalether.core.request.Transaction
import scalether.domain.Address

import scala.language.higherKinds

abstract class AbstractTransactionSender[F[_]](ethereum: Ethereum[F], from: Address, gas: BigInteger, gasPrice: BigInteger)
  extends TransactionSender[F] {

  def call(transaction: Transaction) = ethereum.ethCall(transaction)

  protected def fill(transaction: Transaction) = transaction.copy(
    from = Some(from),
    gas = transaction.gas.orElse(Some(gas)),
    gasPrice = transaction.gasPrice.orElse(Some(gasPrice))
  )
}
