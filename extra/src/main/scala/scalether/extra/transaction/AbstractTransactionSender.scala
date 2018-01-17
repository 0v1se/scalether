package scalether.extra.transaction

import java.math.BigInteger

import cats.Monad
import cats.implicits._
import scalether.core.Ethereum
import scalether.domain.Address
import scalether.domain.request.Transaction

import scala.language.higherKinds

abstract class AbstractTransactionSender[F[_]: Monad](val ethereum: Ethereum[F], val from: Address, val gas: BigInteger, val gasPrice: GasPriceProvider[F])
  extends TransactionSender[F] {

  def call(transaction: Transaction): F[String] =
    ethereum.ethCall(transaction.copy(from = from), "latest")

  override def estimate(transaction: Transaction): F[BigInteger] =
    ethereum.ethEstimateGas(transaction.copy(from = from), "latest")

  protected def fill(transaction: Transaction): F[Transaction] = gasPrice.gasPrice.map {
    gasPrice =>
      transaction.copy(
        from = from,
        gas = Option(transaction.gas).getOrElse(gas),
        gasPrice = Option(transaction.gasPrice).getOrElse(gasPrice)
      )
  }
}
