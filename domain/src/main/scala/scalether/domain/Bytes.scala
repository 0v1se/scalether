package scalether.domain

import java.math.BigInteger

import scalether.util.{Hex, Padding}

trait Bytes {
  def bytes: Array[Byte]

  def padded: Array[Byte] =
    Padding.padLeft(bytes, scalether.util.Bytes.ZERO)

  def toBigInteger: BigInteger =
    new BigInteger(bytes)

  override def toString =
    Hex.prefixed(bytes)
}
