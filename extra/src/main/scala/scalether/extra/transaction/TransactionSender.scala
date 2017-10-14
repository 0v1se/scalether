package scalether.extra.transaction

import scalether.core.request.Transaction

import scala.language.higherKinds

trait TransactionSender[F[_]] {
  def call(transaction: Transaction): F[String]
  def sendTransaction(transaction: Transaction): F[String]
}
