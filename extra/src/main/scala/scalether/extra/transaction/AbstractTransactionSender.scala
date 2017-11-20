package scalether.extra.transaction

import java.math.BigInteger

import scalether.core.Ethereum
import scalether.domain.Address
import scalether.domain.request.Transaction

import scala.language.higherKinds

abstract class AbstractTransactionSender[F[_]](val ethereum: Ethereum[F], val from: Address, val gas: BigInteger, val gasPrice: BigInteger)
  extends TransactionSender[F] {

  def call(transaction: Transaction) = ethereum.ethCall(
    transaction.copy(from = from), "latest"
  )

  protected def fill(transaction: Transaction): Transaction = transaction.copy(
    from = from,
    gas = Option(transaction.gas).getOrElse(gas),
    gasPrice = Option(transaction.gasPrice).getOrElse(gasPrice)
  )
}
