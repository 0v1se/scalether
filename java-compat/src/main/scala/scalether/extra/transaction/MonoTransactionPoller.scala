package scalether.extra.transaction

import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.extra.timer.implicits._
import scalether.java.implicits._

class MonoTransactionPoller(ethereum: MonoEthereum) extends TransactionPoller[Mono](ethereum) {
  override def waitForTransaction(txHash: Mono[String]) = super.waitForTransaction(txHash)
}
