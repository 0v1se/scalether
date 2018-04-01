package scalether.test

import java.math.BigInteger

import cats.implicits._
import org.scalacheck.Gen
import org.scalatest.FlatSpec
import org.scalatest.prop.PropertyChecks
import scalether.domain.Address
import scalether.domain.response.Log

import scala.util.Try

class AbiTupleIntegrationSpec extends FlatSpec with PropertyChecks with IntegrationSpec {

  val test: IntegrationTest[Try] = IntegrationTest.deployAndWait(sender, poller).get

  val addressAndValue: Gen[(Address, BigInteger)] = for {
    address <- Generators.address
    value <- Gen.chooseNum[Long](0, 100000)
  } yield (address, BigInteger.valueOf(value))

  val list: Gen[List[(Address, BigInteger)]] = Gen.listOf(addressAndValue)

  "AbiEncoder" should "encode array of tuples" in {
    forAll(list) {
      testValues =>
        val hash = test.setRates(testValues.toArray).execute().get
        val receipt = ethereum.ethGetTransactionReceipt(hash).get
        assert(receipt.isDefined)
        assert(receipt.get.success)
        assert(testValues == logsToList(receipt.get.logs))
    }
  }

  it should "encode single struct" in {
    forAll(addressAndValue) {
      case (token, value) =>
        val hash = test.setRate(token, value).execute().get
        val receipt = ethereum.ethGetTransactionReceipt(hash).get
        assert(receipt.isDefined)
        assert(receipt.get.success)
        val rates = logsToList(receipt.get.logs)
        assert(rates.size == 1)
        assert(rates.head == (token, value))
    }
  }

  it should "encode and decode struct" in {
    forAll(addressAndValue) {
      case (token, value) =>
        test.setRate(token, value).execute().get
        val result = test.getRate.call().get
        assert(result == (token, value))
    }
  }

  private def logsToList(logs: List[Log]): List[(Address, BigInteger)] = {
    logs
      .map(log => RateEvent(log))
      .map(ev => (ev.token, ev.value))
  }

}
