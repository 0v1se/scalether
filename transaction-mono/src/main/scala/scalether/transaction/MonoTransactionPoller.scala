package scalether.transaction

import io.daonomic.cats.mono.implicits._
import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.response.TransactionReceipt
import io.daonomic.blockchain.poller.mono.implicits._
import scalether.domain.Word

class MonoTransactionPoller(ethereum: MonoEthereum) extends TransactionPoller[Mono](ethereum) {
  override def waitForTransaction(txHash: Mono[Word]): Mono[TransactionReceipt] =
    super.waitForTransaction(txHash)
}
