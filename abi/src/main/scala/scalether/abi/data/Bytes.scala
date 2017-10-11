package scalether.abi.data

import scalether.util.{Hex, Padding}

trait Bytes {
  def bytes: Array[Byte]

  def padded =
    Padding.padLeft(bytes, scalether.util.Bytes.ZERO)

  override def toString =
    Hex.prefixed(padded)
}
