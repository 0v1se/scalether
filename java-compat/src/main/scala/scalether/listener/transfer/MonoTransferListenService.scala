package scalether.listener.transfer

import java.math.BigInteger

import io.daonomic.blockchain.Blockchain
import io.daonomic.blockchain.transfer.TransferListenService
import reactor.core.publisher.Mono
import scalether.java.implicits._
import scalether.listener.common.{MonoState, MonoStateAdapter}

class MonoTransferListenService(blockchain: Blockchain[Mono], confidence: Int, listener: MonoTransferListener, state: MonoState[BigInteger]) {
  private val scala = new TransferListenService[Mono](blockchain, confidence, new MonoTransferListenerAdapter(listener), new MonoStateAdapter[BigInteger](state))

  def check(block: BigInteger): Mono[Void] =
    scala.check(block).`then`()
}
