package scalether.core.json

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer
import scalether.util.Hex

object BigIntHexSerializer extends StdScalarSerializer[BigInt](classOf[BigInt]) {
  def serialize(value: BigInt, gen: JsonGenerator, provider: SerializerProvider) = {
    gen.writeString(s"0x${Hex.bytesToHex(value.toByteArray)}")
  }
}
