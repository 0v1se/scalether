package scalether.test

import java.math.BigInteger

import cats.implicits._
import org.scalatest.FlatSpec
import scalether.core.{Ethereum, EthereumService, Parity}
import scalether.listener.block.{BlockListenService, BlockListener}
import scalether.listener.common.VarState
import scalether.listener.transfer.{TransferListenService, TransferListener}
import scalether.transport.ScalajHttpTransportService

import scala.util.{Failure, Try}

class TransferListenerIntegrationSpec extends FlatSpec {
  val ethereum = new Ethereum[Try](new EthereumService[Try](new ScalajHttpTransportService("http://ether-dev:8545"), log = false))
  val parity = new Parity[Try](new EthereumService[Try](new ScalajHttpTransportService("http://ether-dev:8545"), log = false))

  "TranferListenService" should "listen for transfers" in {

    val transferListenService = new TransferListenService[Try](ethereum, parity, 12, TestTransferListener, new VarState[BigInteger, Try](None))
    val blockListenService = new BlockListenService[Try](ethereum, new TestBlockListener(transferListenService), new VarState[BigInteger, Try](None))

    for (_ <- 1 to 100) {
      blockListenService.check() match {
        case Failure(th) => th.printStackTrace()
        case _ =>
      }
      Thread.sleep(1000)
    }
  }

  it should "notify about transfers in selected block" in {
    val transferListenService = new TransferListenService[Try](ethereum, parity, 12, TestTransferListener, new VarState[BigInteger, Try](None))

    val start = System.currentTimeMillis()
    transferListenService.fetchAndNotify(BigInteger.valueOf(4424611))(BigInteger.valueOf(4424611))
    println(s"took: ${System.currentTimeMillis() - start}ms")
  }
}

class TestBlockListener(service: TransferListenService[Try]) extends BlockListener[Try] {
  override def enabled: Boolean = true

  override def onBlock(block: BigInteger): Try[Unit] = {
    val start = System.currentTimeMillis()
    val result = service.check(block)
    println(s"block: $block took: ${System.currentTimeMillis() - start}ms")
    result
  }
}

object TestTransferListener extends TransferListener[Try] {
  override def enabled: Boolean = true

  override def onTransfer(transfer: scalether.listener.transfer.Transfer, confirmations: Int, confirmed: Boolean): Try[Unit] = Try {
    println(s"$transfer confirmations=$confirmations confirmed=$confirmed")
  }
}
