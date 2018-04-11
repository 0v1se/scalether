package scalether.domain

import scalether.util.Hex

case class Binary(bytes: Array[Byte]) extends Bytes {

}

object Binary {
  def apply(hex: String): Binary =
    new Binary(Hex.toBytes(hex))
}
