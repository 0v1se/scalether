package scalether.generator

import org.scalatest.FlatSpec
import scalether.abi.json.TruffleContract
import scalether.core.json.JsonConverter
import scalether.test.ManualTag

import scala.io.Source

class ContractGeneratorSpec extends FlatSpec {
  val converter = new JsonConverter
  val generator = new ContractGenerator

  def generate(name: String): String = {
    val json = Source.fromResource(s"$name.json").mkString
    val truffle = converter.fromJson[TruffleContract](json)
    generator.generate(truffle, "org.daomao.contract", Type.SCALA)
  }

  "Generator" should "generate IssuedToken" taggedAs ManualTag in {
    println(generate("IssuedToken"))
  }

  it should "generate Token" taggedAs ManualTag in {
    println(generate("Token"))
  }
}
