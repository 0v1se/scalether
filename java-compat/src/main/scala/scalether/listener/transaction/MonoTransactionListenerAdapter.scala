package scalether.listener.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono

class MonoTransactionListenerAdapter(listener: MonoTransactionListener) extends TransactionListener[Mono] {
  override def enabled: Boolean = listener.isEnabled

  override def onTransaction(transactionHash: String, blockHash: String, blockNumber: BigInteger, confirmations: Int, confirmed: Boolean): Mono[Unit] =
    listener.onTransaction(transactionHash, blockHash, blockNumber, confirmations, confirmed)
      .`then`(Mono.just())
}
