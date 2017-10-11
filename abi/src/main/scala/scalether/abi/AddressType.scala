package scalether.abi

import scalether.core.data.Address

object AddressType extends Type[Address] {
  def string = "address"

  def encode(value: Address) =
    value.padded

  def decode(bytes: Array[Byte], offset: Int) =
    Decoded(Address(bytes.slice(offset + 12, offset + 32)), offset + 32)
}
