package scalether.abi

import java.nio.charset.StandardCharsets

import cats.Functor
import cats.implicits._
import scalether.util.{Hash, Hex}

import scala.language.higherKinds

object Call {
  def call[T, R, F[_] : Functor](signature: Signature[T, R], arg: T)
                                (exec: String => F[String]): F[R] = {
    exec(encode(signature, arg)).map { s =>
      signature.out.decode(Hex.hexToBytes(s), 0).value
    }
  }

  def encode[T](signature: Signature[T, _], arg: T): String = {
    val id = buildId(signature)
    id + Hex.bytesToHex(signature.in.encode(arg))
  }

  def buildId(signature: Signature[_, _]): String = {
    val bytes = signature.toString.getBytes(StandardCharsets.US_ASCII)
    "0x" + Hex.bytesToHex(Hash.sha3(bytes).slice(0, 4))
  }
}
