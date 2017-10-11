package scalether.core.json

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer
import scalether.core.data.Hash
import scalether.util.Hex

object HashSerializer extends StdScalarSerializer[Hash](classOf[Hash]) {
  def serialize(value: Hash, gen: JsonGenerator, provider: SerializerProvider) =
    gen.writeString(Hex.prefixed(value.bytes))
}
