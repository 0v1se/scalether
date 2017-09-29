package scalether.abi

import scalether.abi.util.Padding
import scalether.util.{Bytes, Hex, Padding}

object AddressType extends Type[String] {
  def string = "address"

  def encode(value: String) =
    Padding.padLeft(Hex.hexToBytes(value), Bytes.ZERO)

  def decode(bytes: Array[Byte], offset: Int) =
    Decoded(Hex.bytesToHex(bytes.slice(offset + 12, offset + 32)), offset + 32)
}
