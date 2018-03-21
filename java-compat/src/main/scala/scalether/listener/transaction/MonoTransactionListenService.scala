package scalether.listener.transaction

import java.math.BigInteger

import io.daonomic.blockchain.Blockchain
import io.daonomic.blockchain.transaction.TransactionListenService
import reactor.core.publisher.Mono
import scalether.java.implicits._
import scalether.listener.common.{MonoState, MonoStateAdapter}

class MonoTransactionListenService(blockchain: Blockchain[Mono], confidence: Int, listener: MonoTransactionListener, state: MonoState[BigInteger]) {
  private val scala = new TransactionListenService[Mono](blockchain, confidence, new MonoTransactionListenerAdapter(listener), new MonoStateAdapter[BigInteger](state))

  def check(block: BigInteger): Mono[Void] =
    scala.check(block).`then`()
}
