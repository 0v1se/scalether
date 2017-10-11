package scalether.core.json

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer
import scalether.core.data.Address
import scalether.util.Hex

object AddressSerializer extends StdScalarSerializer[Address](classOf[Address]) {
  def serialize(value: Address, gen: JsonGenerator, provider: SerializerProvider) =
    gen.writeString(Hex.prefixed(value.bytes))
}
