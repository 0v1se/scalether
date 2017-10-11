package scalether.abi.data

import scalether.util.Hex

case class Hash(bytes: Array[Byte]) extends Bytes {
  assert(bytes.length == 32)
}

object Hash {
  def apply(hex: String): Hash =
    Hash(Hex.toBytes(hex))
}
