package scalether.listener.transaction

import reactor.core.publisher.Mono

class MonoTransactionListenerAdapter(listener: MonoTransactionListener) extends TransactionListener[Mono] {
  override def enabled: Boolean = listener.isEnabled

  override def onTransaction(txHash: String, confirmations: Int, confirmed: Boolean): Mono[Unit] =
    listener.onTransaction(txHash, confirmations, confirmed)
      .`then`(Mono.just())
}
