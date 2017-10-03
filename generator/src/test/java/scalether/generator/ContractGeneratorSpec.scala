package scalether.generator

import org.scalatest.FlatSpec
import scalether.core.json.JsonConverter

import scala.io.Source

class ContractGeneratorSpec extends FlatSpec {
  val converter = new JsonConverter
  val generator = new ContractGenerator

  def generate(name: String) = {
    val json = Source.fromResource(s"$name.json").mkString
    val truffle = converter.fromJson[TruffleContract](json)
    generator.generate(truffle, "org.daomao.contract")
  }

  "Generator" should "generate IssuedToken" in {
    println(generate("IssuedToken"))
  }

  "Generator" should "generate ERC20" in {
    println(generate("ERC20"))
  }

  "Generator" should "generate ERC20Basic" in {
    println(generate("ERC20Basic"))
  }
}
