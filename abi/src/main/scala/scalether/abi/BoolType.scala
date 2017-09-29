package scalether.abi

import scalether.util.{Bytes, Padding}

object BoolType extends Type[Boolean] {
  val FALSE = Bytes.filled(32, Bytes.ZERO)
  val TRUE = Padding.padLeft(Array(Bytes.ONE), Bytes.ZERO)

  def string = "bool"

  def encode(value: Boolean) = if (value) TRUE else FALSE

  def decode(bytes: Array[Byte], offset: Int) = Decoded(bytes(31) == 0x1, offset + 32)
}