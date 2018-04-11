package scalether.domain

import scalether.util.Hex

case class Word(bytes: Array[Byte]) extends Bytes {
  assert(bytes.length == 32)

  def toBinary: Binary = Binary(bytes)
}

object Word {
  def apply(hex: String): Word =
    Word(Hex.toBytes(hex))

  def apply(binary: Binary): Word =
    Word(binary.bytes)
}
