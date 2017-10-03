package scalether.generator

import java.io._
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

import com.fasterxml.jackson.annotation.JsonProperty
import freemarker.template.Configuration
import scalether.abi.json.AbiItem
import scalether.core.json.JsonConverter
import scalether.generator.template.{ResourceTemplateLoader, ScalaObjectWrapper}

import scala.io.Source

class ContractGenerator {
  private val converter = new JsonConverter
  private val configuration = new Configuration(Configuration.VERSION_2_3_23)
  configuration.setTemplateLoader(new ResourceTemplateLoader("templates"))
  configuration.setDefaultEncoding(StandardCharsets.UTF_8.displayName())
  configuration.setObjectWrapper(new ScalaObjectWrapper)

  def generate(contract: TruffleContract, packageName: String): String = {
    val bytes = new ByteArrayOutputStream()
    cleanly(bytes) { out =>
      cleanly(new OutputStreamWriter(out)) { writer =>
        val model = Map(
          "truffle" -> contract,
          "package" -> packageName,
          "abi" -> escape(converter.toJson(contract.abi))
        )
        generate("contract", model, writer)
        writer.flush()
      }
      out.flush()
    }
    new String(bytes.toByteArray)
  }

  private def generate(template: String, model: Map[String, AnyRef], out: Writer): Unit = {
    configuration.getTemplate(template).process(model, out)
  }

  def escape(raw: String): String = {
    import scala.reflect.runtime.universe._
    Literal(Constant(raw)).toString
  }

  private def cleanly[A <: Closeable, B](resource: => A)(code: A => B): B = {
    val r = resource
    try {
      code(r)
    } finally {
      r.close()
    }
  }
}

object ContractGenerator {
  private val generator = new ContractGenerator

  def main(args: Array[String]): Unit = {
    generate(args(0), args(1), args(2))
  }

  def generate(jsonPath: String, sourcePath: String, packageName: String): Unit = {
    val truffle = generator.converter.fromJson[TruffleContract](Source.fromFile(jsonPath).mkString)
    val source = generator.generate(truffle, packageName)
    val resultPath = sourcePath + "/" + packageName.replace(".", "/")
    new File(resultPath).mkdirs()
    Files.write(Paths.get(resultPath + "/" + truffle.name + ".scala"), source.getBytes())
  }
}

case class TruffleContract(@JsonProperty("contract_name") name: String,
                           @JsonProperty("abi") abi: List[AbiItem],
                           @JsonProperty("unlinked_binary") bin: String)