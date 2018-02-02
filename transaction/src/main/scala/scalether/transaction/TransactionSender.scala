package scalether.transaction

import java.math.BigInteger

import scalether.core.Ethereum
import scalether.domain.request.Transaction

import scala.language.higherKinds

trait TransactionSender[F[_]] {
  val ethereum: Ethereum[F]
  def call(transaction: Transaction): F[Array[Byte]]
  def estimate(transaction: Transaction): F[BigInteger]
  def sendTransaction(transaction: Transaction): F[String]
}
