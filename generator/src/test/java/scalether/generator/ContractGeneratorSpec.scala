package scalether.generator

import org.scalatest.FlatSpec
import scalether.core.json.JsonConverter

import scala.io.Source

class ContractGeneratorSpec extends FlatSpec {
  val json = new JsonConverter
  val generator = new ContractGenerator

  "Generator" should "generate for Token" in {
    val token = Source.fromResource("IssuedToken.json").mkString
    val truffle = json.fromJson[TruffleContract](token)
    println(generator.generate(truffle, "daomao.contract"))
  }
}
