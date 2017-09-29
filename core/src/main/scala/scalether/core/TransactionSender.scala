package scalether.core

import scalether.core.request.Transaction

import scala.language.higherKinds

trait TransactionSender[F[_]] {
  def sendTransaction(transaction: Transaction): F[String]
}
