package scalether.core.bigint

import java.math.BigInteger

import scala.language.implicitConversions

object implicits {
  implicit def intToBigInteger(i: Int): BigInteger = BigInteger.valueOf(i)
  implicit def longToBigInteger(i: Long): BigInteger = BigInteger.valueOf(i)
}
