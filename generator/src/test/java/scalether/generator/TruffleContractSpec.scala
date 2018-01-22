package scalether.generator

import org.scalatest.FlatSpec
import scalether.abi.json.TruffleContract
import scalether.core.json.JsonConverter

import scala.io.Source

class TruffleContractSpec extends FlatSpec {
  val json = new JsonConverter

  "TruffleContract" should "be deserialized" in {
    val token = Source.fromResource("IssuedToken.json").mkString
    val truffle = json.fromJson[TruffleContract](token)
    assert(truffle.abi.lengthCompare(14) == 0)
    assert(truffle.name == "IssuedToken")
    assert(truffle.bin != null)
  }
}
