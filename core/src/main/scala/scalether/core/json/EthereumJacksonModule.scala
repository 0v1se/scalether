package scalether.core.json

import java.math.BigInteger

import com.fasterxml.jackson.databind.module.SimpleModule
import scalether.domain.jackson._
import scalether.domain.request.TopicFilter
import scalether.domain.{Address, Binary, Word}

class EthereumJacksonModule extends SimpleModule {
  addDeserializer(classOf[BigInteger], BigIntegerHexDeserializer)
  addSerializer(classOf[BigInteger], BigIntegerHexSerializer)
  addDeserializer(classOf[Address], AddressDeserializer)
  addSerializer(classOf[Address], AddressSerializer)
  addDeserializer(classOf[Binary], BinaryDeserializer)
  addSerializer(classOf[Binary], BinarySerializer)
  addDeserializer(classOf[Word], WordDeserializer)
  addSerializer(classOf[Word], WordSerializer)
  addSerializer(classOf[TopicFilter], TopicFilterSerializer)
}
