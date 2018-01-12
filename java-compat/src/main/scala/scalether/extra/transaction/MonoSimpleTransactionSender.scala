package scalether.extra.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.Address
import scalether.domain.request.Transaction
import scalether.java.implicits._

class MonoSimpleTransactionSender(ethereum: MonoEthereum, from: Address, gas: BigInteger, gasPrice: MonoGasPriceProvider)
  extends SimpleTransactionSender[Mono](ethereum, from, gas, gasPrice) with MonoTransactionSender {

  override def sendTransaction(transaction: Transaction): Mono[String] = super.sendTransaction(transaction)

  override def call(transaction: Transaction): Mono[String] = super.call(transaction)

  override def estimate(transaction: Transaction): Mono[BigInteger] = super.estimate(transaction)
}
