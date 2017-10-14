package scalether.core

import java.math.BigInteger

import scalether.core.data.Address

import scala.language.implicitConversions

object implicits {
  implicit def intToBigInteger(i: Int): BigInteger = BigInteger.valueOf(i)

  implicit def longToBigInteger(i: Long): BigInteger = BigInteger.valueOf(i)

  implicit def stringToAddress(s: String): Address = Address(s)
}
