package scalether.core.json

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken.VALUE_STRING
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer
import scalether.util.Hex

object BytesDeserializer extends StdScalarDeserializer[Array[Byte]](classOf[Array[Byte]]) {
  def deserialize(jp: JsonParser, ctxt: DeserializationContext): Array[Byte] = jp.getCurrentToken match {
    case VALUE_STRING => Hex.toBytes(jp.getText.trim)
    case _ => ctxt.handleUnexpectedToken(_valueClass, jp).asInstanceOf[Array[Byte]]
  }
}
