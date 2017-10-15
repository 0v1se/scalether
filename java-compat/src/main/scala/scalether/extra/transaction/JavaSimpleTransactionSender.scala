package scalether.extra.transaction

import java.math.BigInteger
import java.util.concurrent.CompletableFuture

import scalether.core.JavaEthereum
import scalether.domain.Address

class JavaSimpleTransactionSender(ethereum: JavaEthereum, from: Address, gas: BigInteger, gasPrice: BigInteger)
  extends SimpleTransactionSender[CompletableFuture](ethereum, from, gas, gasPrice) with JavaTransactionSender {

}
