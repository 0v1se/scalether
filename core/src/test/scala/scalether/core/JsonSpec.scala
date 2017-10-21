package scalether.core

import java.math.BigInteger

import org.scalatest.{FlatSpec, Matchers}
import scalether.core.json.JsonConverter
import scalether.domain.Response

class JsonSpec extends FlatSpec with Matchers {
  val json = new JsonConverter

  "JsonConverter" should "deserialize responses with BigInteger" in {
    val result = json.fromJson[Response[BigInteger]]("{\"id\":1,\"result\":\"0x4A817C800\"}")
    assert(result.result.get == new BigInteger("20000000000"))
  }

  "JsonConverter" should "serialize BigIntegers" in {
    val result = json.toJson(BigInteger.valueOf(0))
    assert(result == "0x0")
  }
}
