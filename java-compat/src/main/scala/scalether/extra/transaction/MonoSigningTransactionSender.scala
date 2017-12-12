package scalether.extra.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.request.Transaction
import scalether.domain.{Address, Word}
import scalether.java.implicits._

class MonoSigningTransactionSender(ethereum: MonoEthereum, nonceProvider: MonoNonceProvider, privateKey: BigInteger, gas: BigInteger, gasPrice: BigInteger)
  extends SigningTransactionSender[Mono](ethereum, nonceProvider, privateKey, gas, gasPrice) with MonoTransactionSender {

  override def sendTransaction(transaction: Transaction): Mono[String] = super.sendTransaction(transaction)

  override def call(transaction: Transaction): Mono[String] = super.call(transaction)

  override protected def fill(transaction: Transaction): Transaction = super.fill(transaction)
}
