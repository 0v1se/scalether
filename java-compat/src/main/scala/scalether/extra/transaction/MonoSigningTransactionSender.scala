package scalether.extra.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.java.implicits._
import scalether.transaction.SigningTransactionSender

class MonoSigningTransactionSender(ethereum: MonoEthereum, nonceProvider: MonoNonceProvider, privateKey: BigInteger, gas: BigInteger, gasPrice: MonoGasPriceProvider)
  extends SigningTransactionSender[Mono](ethereum, nonceProvider, privateKey, gas, gasPrice) with MonoTransactionSender {

}
