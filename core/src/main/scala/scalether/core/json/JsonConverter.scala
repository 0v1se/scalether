package scalether.core.json

import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

import scala.reflect.Manifest

class JsonConverter {
  val objectMapper = new ObjectMapper() with ScalaObjectMapper
  objectMapper.registerModule(DefaultScalaModule)
  objectMapper.registerModule(bigIntModule)
  objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  private def bigIntModule: SimpleModule = {
    val mod = new SimpleModule()
    mod.addDeserializer(classOf[BigInt], BigIntHexDeserializer)
    mod
  }

  def toJson[A <: AnyRef](a: A): String =
    objectMapper.writeValueAsString(a)

  def fromJson[A <: AnyRef](json: String)(implicit mf: Manifest[A]): A =
    objectMapper.readValue(json)
}
