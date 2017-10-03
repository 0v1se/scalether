package scalether.generator

import java.io.{ByteArrayOutputStream, Closeable, OutputStreamWriter, Writer}
import java.nio.charset.StandardCharsets

import com.fasterxml.jackson.annotation.JsonProperty
import freemarker.template.Configuration
import scalether.abi.json.AbiItem
import scalether.generator.template.{ResourceTemplateLoader, ScalaObjectWrapper}

class ContractGenerator {
  private val configuration = new Configuration(Configuration.VERSION_2_3_23)
  configuration.setTemplateLoader(new ResourceTemplateLoader("templates"))
  configuration.setDefaultEncoding(StandardCharsets.UTF_8.displayName())
  configuration.setObjectWrapper(new ScalaObjectWrapper)

  def generate(contract: TruffleContract, packageName: String): String = {
    val bytes = new ByteArrayOutputStream()
    cleanly(bytes) { out =>
      cleanly(new OutputStreamWriter(out)) { writer =>
        generate("contract", Map("truffle" -> contract, "package" -> packageName), writer)
        writer.flush()
      }
      out.flush()
    }
    new String(bytes.toByteArray)
  }

  private def generate(template: String, model: Map[String, AnyRef], out: Writer): Unit = {
    configuration.getTemplate(template).process(model, out)
  }

  def cleanly[A <: Closeable, B](resource: => A)(code: A => B): B = {
    val r = resource
    try {
      code(r)
    } finally {
      r.close()
    }
  }
}

case class TruffleContract(@JsonProperty("contract_name") name: String,
                           @JsonProperty("abi") abi: List[AbiItem],
                           @JsonProperty("unlinked_binary") bin: String)