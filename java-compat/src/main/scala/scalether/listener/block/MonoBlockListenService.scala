package scalether.listener.block

import java.math.BigInteger

import scalether.java.implicits._
import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.listener.common.{MonoState, MonoStateAdapter}

class MonoBlockListenService(ethereum: MonoEthereum, listener: MonoBlockListener, state: MonoState[BigInteger]) {
  private val scala = new BlockListenService[Mono](ethereum, new MonoBlockListenerAdapter(listener), new MonoStateAdapter[BigInteger](state))

  def check(): Mono[BigInteger] =
    scala.check()
}
