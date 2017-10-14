package scalether.test

import java.math.BigInteger

import cats.implicits._
import org.scalacheck.Gen
import org.scalatest.FlatSpec
import org.scalatest.prop.PropertyChecks
import scalether.core.request.Transaction
import scalether.core.{Ethereum, EthereumService}
import scalether.domain.implicits._
import scalether.extra.timer.tries.implicits._
import scalether.extra.transaction.{SigningTransactionSender, TransactionPoller}
import scalether.transport.ScalajHttpTransportService

import scala.util.Try

class TransactionSignerIntegrationSpec extends FlatSpec with PropertyChecks {
  val ethereum = new Ethereum[Try](new EthereumService[Try](new ScalajHttpTransportService("http://localhost:8545"), log = true))
  val sender = new SigningTransactionSender[Try](
    ethereum, "0xc66d094ed928f7840a6b0d373c1cd825c97e3c7c", "0x00120de4b1518cf1f16dc1b02f6b4a8ac29e870174cb1d8575f578480930250a", 2000000, 10)
  val poller = new TransactionPoller[Try](ethereum)
  val initial: BigInteger = "50000000000000000000"

  val addressAndValue = for {
    address <- Generators.address
    value <- Gen.chooseNum[Long](0, 100000)
  } yield (address, BigInteger.valueOf(value))

  "Signer" should "sign simple ether transaction" in {
    forAll(addressAndValue) {
      case (address, value) =>
        poller.waitForTransaction(sender.sendTransaction(Transaction(to = Some(address), value = value)))
        assert(ethereum.ethGetBalance(address, "latest").get == value)
    }
  }

  it should "sign contract deployments and calls" in {
    forAll(Gen.posNum[Long]) { value =>
      val test = IntegrationTest.deployAndWait(sender, poller).get
      poller.waitForTransaction(test.setState(value))
      assert(test.state.get == (value: BigInteger))
    }
  }
}
