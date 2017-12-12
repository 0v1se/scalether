package scalether.core.json

import java.math.BigInteger

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer
import scalether.util.Hex

object BigIntegerHexSerializer extends StdScalarSerializer[BigInteger](classOf[BigInteger]) {
  def serialize(value: BigInteger, gen: JsonGenerator, provider: SerializerProvider): Unit = {
    gen.writeString(s"0x${value.toString(16)}")
  }
}
