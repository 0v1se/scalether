package scalether.abi

import scalether.abi.util.{Bytes, Hex, Padding}

object AbiTestConst {
  def fill(size: Int, byte: Byte): Array[Byte] = List.fill(size)(byte).toArray

  val zero = new Array[Byte](32)
  val one = Padding.padLeft(Array[Byte](1.toByte), Bytes.ZERO)
  val ten = Padding.padLeft(Array[Byte](10.toByte), Bytes.ZERO)
  val minusOne = Hex.hexToBytes(List.fill(64)('f').mkString)
  val maxLong = Hex.hexToBytes(List.fill(48)('0').mkString + "7fffffffffffffff")
  val minLong = Hex.hexToBytes(List.fill(48)('f').mkString + "8000000000000000")
}
