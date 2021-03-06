package scalether.test

import java.math.BigInteger

import cats.implicits._
import io.daonomic.blockchain.block.{BlockListenService, BlockListener}
import io.daonomic.blockchain.state.VarState
import io.daonomic.blockchain.transfer
import io.daonomic.blockchain.transfer.{TransferListenService, TransferListener}
import io.daonomic.rpc.tries.ScalajHttpTransport
import org.scalatest.FlatSpec
import scalether.core.{Ethereum, Parity}
import scalether.listener.EthereumBlockchain
import io.daonomic.blockchain.poller.tries.implicits._

import scala.util.{Failure, Try}

class TransferListenerIntegrationSpec extends FlatSpec {
  val transport = new ScalajHttpTransport("http://ether-dev:8545")
  val ethereum = new Ethereum[Try](transport)
  val parity = new Parity[Try](transport)
  val blockchain = new EthereumBlockchain(ethereum, parity)

  "TranferListenService" should "listen for transfers" taggedAs ManualTag in {

    val transferListenService = new TransferListenService[Try](blockchain, 2, TestTransferListener, new VarState[BigInteger, Try](None))
    val blockListenService = new BlockListenService[Try](blockchain, new TestBlockListener(transferListenService), new VarState[BigInteger, Try](None))

    for (_ <- 1 to 100) {
      blockListenService.check() match {
        case Failure(th) => th.printStackTrace()
        case _ =>
      }
      Thread.sleep(1000)
    }
  }

  it should "notify about transfers in selected block" taggedAs ManualTag in {
    val transferListenService = new TransferListenService[Try](blockchain, 1, TestTransferListener, new VarState[BigInteger, Try](None))

    val start = System.currentTimeMillis()
    transferListenService.fetchAndNotify(BigInteger.valueOf(5000099))(BigInteger.valueOf(5000099))
    println(s"took: ${System.currentTimeMillis() - start}ms")
  }
}

class TestBlockListener(service: TransferListenService[Try]) extends BlockListener[Try] {
  override def onBlock(block: BigInteger): Try[Unit] = {
    val start = System.currentTimeMillis()
    val result = service.check(block)
    println(s"block: $block took: ${System.currentTimeMillis() - start}ms")
    result
  }
}

object TestTransferListener extends TransferListener[Try] {
  override def onTransfer(t: transfer.Transfer, confirmations: Int, confirmed: Boolean): Try[Unit] = Try {
    println(s"$t confirmations=$confirmations confirmed=$confirmed")
  }
}
