package scalether.extra.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.domain.request.Transaction
import scalether.transaction.TransactionSender

trait MonoTransactionSender extends TransactionSender[Mono] {
  def call(transaction: Transaction): Mono[String]
  def estimate(transaction: Transaction): Mono[BigInteger]
  def sendTransaction(transaction: Transaction): Mono[String]
}
