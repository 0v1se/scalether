package scalether.core.transaction

import java.math.BigInteger

import scalether.java.implicits._
import java.util.concurrent.CompletableFuture

import scalether.core.{JavaEthereum, JavaTransactionSender}

class JavaSimpleTransactionSender(ethereum: JavaEthereum, from: String, gas: BigInteger, gasPrice: BigInteger)
  extends SimpleTransactionSender[CompletableFuture](ethereum, from, gas, gasPrice) with JavaTransactionSender {

}
