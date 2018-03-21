package scalether.transaction

import io.daonomic.cats.mono.implicits._
import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.response.TransactionReceipt
import scalether.poller.mono.implicits._

class MonoTransactionPoller(ethereum: MonoEthereum) extends TransactionPoller[Mono](ethereum) {
  override def waitForTransaction(txHash: Mono[String]): Mono[TransactionReceipt] = super.waitForTransaction(txHash)
}
