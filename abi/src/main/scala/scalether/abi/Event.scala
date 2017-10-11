package scalether.abi

import java.nio.charset.StandardCharsets

import scalether.abi.tuple.TupleType
import scalether.util.{Hash, Hex}

case class Event[IT <: TupleType[_], NI](name: String, types: List[Type[_]], indexed: IT, nonIndexed: TupleType[NI]) {
  def decode(data: String): NI = {
    val bytes = Hex.toBytes(data)
    nonIndexed.decode(bytes, 0).value
  }

  override def toString = s"$name(${types.map(_.string).mkString(",")})"

  def id = {
    val bytes = toString.getBytes(StandardCharsets.US_ASCII)
    Hex.toPrefixed(Hash.sha3(bytes))
  }
}
