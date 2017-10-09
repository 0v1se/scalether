package scalether.abi.data

import scalether.util.{Bytes, Hex, Padding}

case class Address(bytes: Array[Byte]) {
  assert(bytes.length == 20)

  def padded =
    Padding.padLeft(bytes, Bytes.ZERO)

  override def toString =
    "0x" + Hex.bytesToHex(padded)
}

object Address {
  def apply(hex: String) = new Address(Hex.hexToBytes(hex))
}
