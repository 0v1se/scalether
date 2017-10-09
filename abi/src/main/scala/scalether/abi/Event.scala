package scalether.abi

import java.nio.charset.StandardCharsets

import scalether.util.{Hash, Hex}

case class Event[T](name: String, `type`: Type[T]) {
  override def toString = name + `type`.string
  def id = {
    val bytes = toString.getBytes(StandardCharsets.US_ASCII)
    "0x" + Hex.bytesToHex(Hash.sha3(bytes))
  }
}
