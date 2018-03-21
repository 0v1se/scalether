package scalether.listener.block

import java.math.BigInteger

import io.daonomic.blockchain.block.BlockListener
import reactor.core.publisher.Mono

class MonoBlockListenerAdapter(listener: MonoBlockListener) extends BlockListener[Mono] {
  override def onBlock(block: BigInteger): Mono[Unit] =
    listener.onBlock(block)
      .`then`(Mono.just())
}
