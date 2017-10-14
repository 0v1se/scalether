package scalether.core.transaction

import java.util.concurrent.CompletableFuture

import scalether.core.JavaEthereum
import scalether.extra.transaction.TransactionPoller
import scalether.java.implicits._
import scalether.util.timer.implicits._

class JavaTransactionPoller(ethereum: JavaEthereum) extends TransactionPoller[CompletableFuture](ethereum) {
  override def waitForTransaction(txHash: CompletableFuture[String]) = super.waitForTransaction(txHash)
}
