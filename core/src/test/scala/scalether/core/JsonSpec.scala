package scalether.core

import java.math.BigInteger

import org.scalatest.{FlatSpec, Matchers}
import scalether.core.json.JsonConverter
import scalether.domain.{Response, Word}
import scalether.domain.request.{LogFilter, SimpleTopicFilter, TopicFilter}

class JsonSpec extends FlatSpec with Matchers {
  val json = new JsonConverter

  "JsonConverter" should "deserialize responses with BigInteger" in {
    val result = json.fromJson[Response[BigInteger]]("{\"id\":1,\"result\":\"0x4A817C800\"}")
    assert(result.result.get == new BigInteger("20000000000"))
  }

  it should "serialize BigIntegers" in {
    val result = json.toJson(BigInteger.valueOf(0))
    assert(result == "\"0x0\"")
  }

  it should "serialize simple LogFilter" in {
    val result = json.toJson(LogFilter(topics = List(Word("0x5742ce6d6b60075574d7aca76464bc56ccc67f0edcab8ab1b0caa30cbf79056d"))))
    assert(result == "{\"topics\":[\"0x5742ce6d6b60075574d7aca76464bc56ccc67f0edcab8ab1b0caa30cbf79056d\"],\"address\":[],\"fromBlock\":\"latest\",\"toBlock\":\"latest\"}")
  }

  it should "serialize or LogFilter" in {
    val result = json.toJson(LogFilter(topics = List(TopicFilter.or(Word("0x5742ce6d6b60075574d7aca76464bc56ccc67f0edcab8ab1b0caa30cbf79056d"), Word("0x5742ce6d6b60075574d7aca76464bc56ccc67f0edcab8ab1b0caa30cbf79056d")))))
    assert(result == "{\"topics\":[[\"0x5742ce6d6b60075574d7aca76464bc56ccc67f0edcab8ab1b0caa30cbf79056d\",\"0x5742ce6d6b60075574d7aca76464bc56ccc67f0edcab8ab1b0caa30cbf79056d\"]],\"address\":[],\"fromBlock\":\"latest\",\"toBlock\":\"latest\"}")
  }

}
