package scalether.abi

import java.nio.charset.StandardCharsets

import scalether.abi.tuple.TupleType
import scalether.util.{Hash, Hex}

case class Signature[I, O](name: String, in: TupleType[I], out: TupleType[O]) {
  def id = {
    val bytes = toString.getBytes(StandardCharsets.US_ASCII)
    Hex.toPrefixed(Hash.sha3(bytes).slice(0, 4))
  }

  override def toString: String = name + in.string
}
