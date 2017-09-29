package scalether.core.transaction

import scalether.core.request.Transaction
import scalether.core.{Ethereum, TransactionSender}

import scala.language.higherKinds

abstract class TransactionSigner[F[_]](ethereum: Ethereum[F]) extends TransactionSender[F] {
  def sendTransaction(transaction: Transaction): F[String]
}
