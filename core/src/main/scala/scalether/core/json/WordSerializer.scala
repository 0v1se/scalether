package scalether.core.json

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer
import scalether.domain.Word
import scalether.util.Hex

object WordSerializer extends StdScalarSerializer[Word](classOf[Word]) {
  def serialize(value: Word, gen: JsonGenerator, provider: SerializerProvider) =
    gen.writeString(Hex.prefixed(value.bytes))
}