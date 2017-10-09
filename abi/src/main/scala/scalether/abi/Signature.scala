package scalether.abi

import java.nio.charset.StandardCharsets

import scalether.util.{Hash, Hex}

case class Signature[I, O](name: String, in: Type[I], out: Type[O]) {
  def id = {
    val bytes = toString.getBytes(StandardCharsets.US_ASCII)
    "0x" + Hex.bytesToHex(Hash.sha3(bytes).slice(0, 4))
  }

  override def toString: String = name + in.string
}
