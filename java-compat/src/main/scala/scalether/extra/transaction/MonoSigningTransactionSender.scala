package scalether.extra.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.request.Transaction
import scalether.domain.{Address, Word}
import scalether.java.implicits._

class MonoSigningTransactionSender(ethereum: MonoEthereum, nonceProvider: MonoNonceProvider, from: Address, key: Word, gas: BigInteger, gasPrice: BigInteger)
  extends SigningTransactionSender[Mono](ethereum, nonceProvider, from, key, gas, gasPrice) with MonoTransactionSender {

  override def sendTransaction(transaction: Transaction) = super.sendTransaction(transaction)

  override def call(transaction: Transaction) = super.call(transaction)

  override protected def fill(transaction: Transaction) = super.fill(transaction)
}
