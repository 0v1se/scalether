package scalether.core.transaction

import java.util.concurrent.CompletableFuture

import scalether.core.JavaEthereum
import scalether.java.implicits._
import scalether.util.timer.implicits._
import scalether.util.transaction.TransactionPoller

class JavaTransactionPoller(ethereum: JavaEthereum) extends TransactionPoller[CompletableFuture](ethereum) {
  override def waitForTransaction(txHash: String) = super.waitForTransaction(txHash)
}
