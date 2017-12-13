package scalether.core.json

import java.math.BigInteger

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import scalether.domain.request.TopicFilter
import scalether.domain.{Address, Word}

import scala.reflect.Manifest

class JsonConverter {
  val objectMapper = new ObjectMapper() with ScalaObjectMapper
  objectMapper.registerModule(DefaultScalaModule)
  objectMapper.registerModule(bigIntModule)
  objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  objectMapper.setSerializationInclusion(Include.NON_NULL)

  private def bigIntModule: SimpleModule = {
    val mod = new SimpleModule()
    mod.addDeserializer(classOf[BigInteger], BigIntegerHexDeserializer)
    mod.addSerializer(classOf[BigInteger], BigIntegerHexSerializer)
    mod.addDeserializer(classOf[Address], AddressDeserializer)
    mod.addSerializer(classOf[Address], AddressSerializer)
    mod.addDeserializer(classOf[Word], WordDeserializer)
    mod.addSerializer(classOf[Word], WordSerializer)
    mod.addSerializer(classOf[TopicFilter], TopicFilterSerializer)
    mod
  }

  def toJson[A <: AnyRef](a: A): String =
    objectMapper.writeValueAsString(a)

  def fromJson[A <: AnyRef](json: String)(implicit mf: Manifest[A]): A =
    objectMapper.readValue(json)
}
