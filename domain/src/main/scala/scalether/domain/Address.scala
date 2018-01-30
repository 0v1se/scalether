package scalether.domain

import java.nio.charset.{Charset, StandardCharsets}

import org.web3j.crypto.Hash
import scalether.util.Hex

case class Address(bytes: Array[Byte]) extends Bytes {
  assert(bytes.length == 20)

  def toChecksumString: String = {
    val s = Hex.to(bytes)
    val hash = Hex.to(Hash.sha3(s.getBytes(StandardCharsets.US_ASCII)))
    "0x" + s.zipWithIndex.map {
      case (char, idx) if Character.digit(hash.charAt(idx), 16) >= 8 =>
        char.toUpper
      case (char, _) =>
        char
    }.mkString
  }
}

object Address {
  def apply(hex: String): Address =
    new Address(Hex.toBytes(hex))
}
