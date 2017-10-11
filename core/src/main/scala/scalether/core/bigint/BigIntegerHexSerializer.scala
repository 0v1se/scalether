package scalether.core.bigint

import java.math.BigInteger

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer
import scalether.util.Hex

object BigIntegerHexSerializer extends StdScalarSerializer[BigInteger](classOf[BigInteger]) {
  def serialize(value: BigInteger, gen: JsonGenerator, provider: SerializerProvider) = {
    gen.writeString(Hex.prefixed(value.toByteArray))
  }
}