package scalether.extra.transaction

import java.math.BigInteger

import cats.MonadError
import scalether.core.Ethereum
import scalether.domain.Address
import scalether.domain.request.Transaction

import scala.language.higherKinds

class ReadOnlyTransactionSender[F[_]](val ethereum: Ethereum[F], val from: Address)
                                     (implicit m: MonadError[F, Throwable])
  extends TransactionSender[F] {

  override def call(transaction: Transaction): F[String] =
    ethereum.ethCall(transaction.copy(from = from), "latest")

  override def estimate(transaction: Transaction): F[BigInteger] =
    ethereum.ethEstimateGas(transaction.copy(from = from), "latest")

  override def sendTransaction(transaction: Transaction): F[String] =
    m.raiseError(new UnsupportedOperationException)
}
