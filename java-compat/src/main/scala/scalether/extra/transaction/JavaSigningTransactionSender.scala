package scalether.extra.transaction

import java.math.BigInteger
import java.util.concurrent.CompletableFuture

import scalether.core.JavaEthereum
import scalether.domain.request.Transaction
import scalether.domain.{Address, Word}
import scalether.java.implicits._

class JavaSigningTransactionSender(ethereum: JavaEthereum, nonceProvider: JavaNonceProvider, from: Address, key: Word, gas: BigInteger, gasPrice: BigInteger)
  extends SigningTransactionSender[CompletableFuture](ethereum, nonceProvider, from, key, gas, gasPrice) with JavaTransactionSender {

  override def sendTransaction(transaction: Transaction) = super.sendTransaction(transaction)

  override def call(transaction: Transaction) = super.call(transaction)

  override protected def fill(transaction: Transaction) = super.fill(transaction)
}
