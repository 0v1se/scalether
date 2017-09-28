package scalether.abi

import java.nio.charset.StandardCharsets

object StringType extends Type[String] {
  def string = "string"

  override def size = None

  def encode(value: String) = {
    val bytes = value.getBytes(StandardCharsets.UTF_8)
    BytesType.encode(bytes)
  }

  def decode(bytes: Array[Byte], offset: Int) = {
    val decoded = BytesType.decode(bytes, offset)
    Decoded(new String(decoded.value, StandardCharsets.UTF_8), decoded.offset)
  }
}
