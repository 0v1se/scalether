package scalether.listener.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.listener.common.{MonoState, MonoStateAdapter}

class MonoTransactionListenService(ethereum: MonoEthereum, confidence: Int, listener: MonoTransactionListener, state: MonoState[BigInteger]) {
  private val scala = new TransactionListenService[Mono](ethereum, confidence, new MonoTransactionListenerAdapter(listener), new MonoStateAdapter[BigInteger](state))

  def check(block: BigInteger): Mono[Void] =
    scala.check(block).`then`()
}
