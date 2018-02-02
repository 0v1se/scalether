package scalether.core.json

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer
import scalether.util.Hex

object BytesSerializer extends StdScalarSerializer[Array[Byte]](classOf[Array[Byte]]) {
  def serialize(value: Array[Byte], gen: JsonGenerator, provider: SerializerProvider): Unit =
    gen.writeString(Hex.prefixed(value))
}
