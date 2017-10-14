package scalether.core.request

import java.math.BigInteger

import scalether.domain.Address

case class Transaction(to: Option[Address] = None,
                       from: Option[Address] = None,
                       gas: Option[BigInteger] = None,
                       gasPrice: Option[BigInteger] = None,
                       value: BigInteger = BigInteger.ZERO,
                       data: String = "",
                       nonce: Option[BigInteger] = None)
