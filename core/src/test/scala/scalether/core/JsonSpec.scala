package scalether.core

import java.math.BigInteger

import org.scalatest.{FlatSpec, Matchers}
import scalether.core.json.JsonConverter

class JsonSpec extends FlatSpec with Matchers {
  val json = new JsonConverter

  "JsonConverter" should "deserialize responses with BigInt" in {
    val result = json.fromJson[Response[BigInteger]]("{\"id\":1,\"result\":\"0x4A817C800\"}")
    assert(result.result.get == new BigInteger("20000000000"))
  }
}
