package scalether.core.data

import scalether.util.Hex

case class Address(bytes: Array[Byte]) extends Bytes {
  assert(bytes.length == 20)
}

object Address {
  def apply(hex: String): Address =
    new Address(Hex.toBytes(hex))
}
