package scalether.listener.block

import java.math.BigInteger

import io.daonomic.blockchain.Blockchain
import io.daonomic.blockchain.newblock.BlockListenService
import reactor.core.publisher.Mono
import scalether.java.implicits._
import scalether.listener.common.{MonoState, MonoStateAdapter}

class MonoBlockListenService(blockchain: Blockchain[Mono], listener: MonoBlockListener, state: MonoState[BigInteger]) {
  private val scala = new BlockListenService[Mono](blockchain, new MonoBlockListenerAdapter(listener), new MonoStateAdapter[BigInteger](state))

  def check(): Mono[BigInteger] =
    scala.check()
}
