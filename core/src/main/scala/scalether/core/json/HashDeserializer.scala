package scalether.core.json

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken.VALUE_STRING
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer
import scalether.core.data.{Address, Hash}

object HashDeserializer extends StdScalarDeserializer[Hash](classOf[Hash]) {
  def deserialize(jp: JsonParser, ctxt: DeserializationContext) = jp.getCurrentToken match {
    case VALUE_STRING => Hash(jp.getText.trim)
    case _ => ctxt.handleUnexpectedToken(_valueClass, jp).asInstanceOf[Hash]
  }
}
