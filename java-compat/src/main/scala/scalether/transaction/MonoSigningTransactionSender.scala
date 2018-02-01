package scalether.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.request.Transaction
import scalether.java.implicits._

class MonoSigningTransactionSender(ethereum: MonoEthereum, nonceProvider: MonoNonceProvider, privateKey: BigInteger, gas: BigInteger, gasPrice: MonoGasPriceProvider)
  extends SigningTransactionSender[Mono](ethereum, nonceProvider, privateKey, gas, gasPrice) with MonoTransactionSender {

  override def sendTransaction(transaction: Transaction): Mono[String] = super.sendTransaction(transaction)
}
