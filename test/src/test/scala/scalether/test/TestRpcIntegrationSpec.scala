package scalether.test

import cats.implicits._
import org.scalatest.FlatSpec
import scalether.abi.data.Address
import scalether.core.bigint.implicits._
import scalether.core.request.LogFilter
import scalether.core.transaction.SimpleTransactionSender
import scalether.core.{Ethereum, EthereumService}
import scalether.test.Events.MixedEvent
import scalether.transport.ScalajHttpTransportService
import scalether.util.timer.tries.Implicits._
import scalether.util.transaction.TransactionPoller

import scala.util.Try

class TestRpcIntegrationSpec extends FlatSpec {
  val ethereum = new Ethereum[Try](new EthereumService[Try](new ScalajHttpTransportService("http://localhost:8545"), log = true))
  val sender = new SimpleTransactionSender[Try](ethereum, "0xc66d094ed928f7840a6b0d373c1cd825c97e3c7c", 2000000, 10)
  val transactionPoller = new TransactionPoller[Try](ethereum)

  "Scalether" should "get logs in receipts" in {
    val events = Events.deployAndWait(sender, transactionPoller)
    val hash = events.get.emitAddressEvent(Address("0xc66d094ed928f7840a6b0d373c1cd825c97e3c7c"), "value").get

    val receipt = transactionPoller.waitForTransaction(hash).get
    println(receipt.logs)
  }

  it should "emit MixedEvent" in {
    val events = Events.deployAndWait(sender, transactionPoller)
    val hash = events.get.emitMixedEvent(Address("0xc66d094ed928f7840a6b0d373c1cd825c97e3c7c"), "value", Address("0xc00000000928f7840a6b0d373c1cd825c97e3c7c")).get

    val receipt = transactionPoller.waitForTransaction(hash).get
    println(receipt.logs.map(log => MixedEvent(log)))
  }

  it should "get logs immediately" in {
    val filter = LogFilter(
      topics = List("0x39b8d23135cdeca3f85b347e5285f40c9b1de764cf9f8126e7f3b34d77ff0cf0"),
      fromBlock = "0x0"
    )
    val logs = ethereum.ethGetLogs(filter).get
    println(logs.size)
    println(logs)
  }

  it should "get logs" in {
    val filter = LogFilter(
      topics = List("0x39b8d23135cdeca3f85b347e5285f40c9b1de764cf9f8126e7f3b34d77ff0cf0"),
      fromBlock = "0x0"
    )
    val id = ethereum.ethNewFilter(filter).get
    val logs = ethereum.ethGetFilterChanges(7)
    println(logs.get.size)
    println(logs.get)
  }
}
