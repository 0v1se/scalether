package scalether.extra.transaction

import java.math.BigInteger
import java.util.concurrent.CompletableFuture

import scalether.core.JavaEthereum
import scalether.domain.{Address, Word}
import scalether.java.implicits._

class JavaSigningTransactionSender(ethereum: JavaEthereum, from: Address, key: Word, gas: BigInteger, gasPrice: BigInteger)
  extends SigningTransactionSender[CompletableFuture](ethereum, from, key, gas, gasPrice) with JavaTransactionSender {

}
