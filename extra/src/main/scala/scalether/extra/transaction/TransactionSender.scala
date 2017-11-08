package scalether.extra.transaction

import scalether.core.Ethereum
import scalether.domain.request.Transaction

import scala.language.higherKinds

trait TransactionSender[F[_]] {
  val ethereum: Ethereum[F]
  def call(transaction: Transaction): F[String]
  def sendTransaction(transaction: Transaction): F[String]
}
