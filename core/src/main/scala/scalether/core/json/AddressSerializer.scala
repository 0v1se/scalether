package scalether.core.json

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer
import scalether.domain.Address
import scalether.util.Hex

object AddressSerializer extends StdScalarSerializer[Address](classOf[Address]) {
  def serialize(value: Address, gen: JsonGenerator, provider: SerializerProvider): Unit =
    gen.writeString(Hex.prefixed(value.bytes))
}
