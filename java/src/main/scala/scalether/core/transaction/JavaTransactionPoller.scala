package scalether.core.transaction

import java.util.concurrent.CompletableFuture

import scalether.core.JavaEthereum
import scalether.java.implicits._
import scalether.util.timer.Implicits._
import scalether.util.transaction.TransactionPoller

class JavaTransactionPoller(ethereum: JavaEthereum) extends TransactionPoller[CompletableFuture](ethereum) {

}
