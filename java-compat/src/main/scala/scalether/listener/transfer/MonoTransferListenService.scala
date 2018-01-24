package scalether.listener.transfer

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.core.{MonoEthereum, MonoParity}
import scalether.java.implicits._
import scalether.listener.common.{MonoState, MonoStateAdapter}

class MonoTransferListenService(ethereum: MonoEthereum, parity: MonoParity, confidence: Int, listener: MonoTransferListener, state: MonoState[BigInteger]) {
  private val scala = new TransferListenService[Mono](ethereum, parity, confidence, new MonoTransferListenerAdapter(listener), new MonoStateAdapter[BigInteger](state))

  def check(block: BigInteger): Mono[Void] =
    scala.check(block).`then`()
}
