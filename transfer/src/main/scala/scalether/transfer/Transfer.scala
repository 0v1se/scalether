package scalether.transfer

import java.math.BigInteger

import scalether.domain.Address

case class Transfer(from: Address, to: Address, value: BigInteger, txHash: String)
