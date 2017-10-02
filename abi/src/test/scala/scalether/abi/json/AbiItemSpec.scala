package scalether.abi.json

import org.scalatest.FlatSpec
import scalether.core.json.JsonConverter

import scala.language.postfixOps

class AbiItemSpec extends FlatSpec {
  val converter = new JsonConverter

  "AbiItem" should "be deserialized correctly" in {
    val items = converter.fromJson[List[AbiItem]]("[{\n\"type\":\"event\",\n\"inputs\": [{\"name\":\"a\",\"type\":\"uint256\",\"indexed\":true},{\"name\":\"b\",\"type\":\"bytes32\",\"indexed\":false}],\n\"name\":\"Event\"\n}, {\n\"type\":\"event\",\n\"inputs\": [{\"name\":\"a\",\"type\":\"uint256\",\"indexed\":true},{\"name\":\"b\",\"type\":\"bytes32\",\"indexed\":false}],\n\"name\":\"Event2\"\n}, {\n\"type\":\"constructor\",\n\"inputs\": [{\"name\":\"a\",\"type\":\"uint256\"}],\n\"name\":\"foo\",\n\"outputs\": []\n}, {\n\"inputs\": [{\"name\":\"a\",\"type\":\"uint256\"}],\n\"name\":\"foo\",\n\"outputs\": []\n}]")
    assert(items.length == 4)
    assert(items.head.isInstanceOf[AbiEvent])
    assert(items.last.isInstanceOf[AbiFunction])
    assert(items(2).asInstanceOf[AbiFunction].getType == AbiFunctionType.CONSTRUCTOR)
    assert(items(3).asInstanceOf[AbiFunction].getType == AbiFunctionType.FUNCTION)

    println(converter.toJson(items(1)))
    println(converter.toJson(items(3)))
    println(converter.toJson(items(2)))
  }
}
