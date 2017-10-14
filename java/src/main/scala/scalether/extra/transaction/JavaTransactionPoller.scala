package scalether.extra.transaction

import java.util.concurrent.CompletableFuture

import scalether.core.JavaEthereum
import scalether.java.implicits._
import scalether.extra.timer.implicits._

class JavaTransactionPoller(ethereum: JavaEthereum) extends TransactionPoller[CompletableFuture](ethereum) {
  override def waitForTransaction(txHash: CompletableFuture[String]) = super.waitForTransaction(txHash)
}
