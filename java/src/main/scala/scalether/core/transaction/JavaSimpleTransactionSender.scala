package scalether.core.transaction

import scalether.java.implicits._
import java.util.concurrent.CompletableFuture

import scalether.core.{JavaEthereum, JavaTransactionSender}

class JavaSimpleTransactionSender(ethereum: JavaEthereum, from: String, gas: BigInt, gasPrice: BigInt)
  extends SimpleTransactionSender[CompletableFuture](ethereum, from, gas, gasPrice) with JavaTransactionSender {

}
