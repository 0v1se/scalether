package scalether.extra.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.Address
import scalether.java.implicits._
import scalether.transaction.SimpleTransactionSender

class MonoSimpleTransactionSender(ethereum: MonoEthereum, from: Address, gas: BigInteger, gasPrice: MonoGasPriceProvider)
  extends SimpleTransactionSender[Mono](ethereum, from, gas, gasPrice) with MonoTransactionSender {
}
