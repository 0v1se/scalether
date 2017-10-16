package scalether.domain.request

import java.math.BigInteger

import scalether.domain.Address

case class Transaction(to: Address = null,
                       from: Address = null,
                       gas: BigInteger = null,
                       gasPrice: BigInteger = null,
                       value: BigInteger = null,
                       data: String = "",
                       nonce: BigInteger = null)
