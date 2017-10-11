package scalether.core.request

import java.math.BigInteger

case class Transaction(to: Option[String] = None,
                       from: Option[String] = None,
                       gas: Option[BigInteger] = None,
                       gasPrice: Option[BigInteger] = None,
                       value: BigInteger = BigInteger.ZERO,
                       data: Option[String] = None,
                       nonce: Option[Int] = None)
