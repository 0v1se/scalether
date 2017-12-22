package scalether.extra.transaction

import reactor.core.publisher.Mono
import scalether.domain.request.Transaction

trait MonoTransactionSender extends TransactionSender[Mono] {
  def call(transaction: Transaction): Mono[String]
  def sendTransaction(transaction: Transaction): Mono[String]
}
