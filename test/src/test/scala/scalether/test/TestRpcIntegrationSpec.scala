package scalether.test

import cats.implicits._
import org.scalatest.FlatSpec
import scalether.core.transaction.SimpleTransactionSender
import scalether.core.{Ethereum, EthereumServiceImpl}
import scalether.transport.ScalajHttpTransportService
import scalether.util.timer.tries.Implicits._
import scalether.util.transaction.TransactionService

import scala.util.Try

class TestRpcIntegrationSpec extends FlatSpec {
  val ethereum = new Ethereum[Try](new EthereumServiceImpl[Try](new ScalajHttpTransportService("http://localhost:8545"), log = true))
  val sender = new SimpleTransactionSender[Try](ethereum, "0xc66d094ed928f7840a6b0d373c1cd825c97e3c7c", 2000000, 10)
  val transactionService = new TransactionService[Try](ethereum)

  "Scalether" should "get logs in receipts" in {
    val events = Events.deployAndWait(sender, transactionService)
    val hash = events.get.emitEvent("topic", "value").get

    val receipt = transactionService.waitForTransaction(hash).get
    println(receipt.logs)
  }
}
