package scalether.abi

import cats.Functor
import cats.implicits._
import scalether.util.Hex

import scala.language.higherKinds

object Call {
  def call[T, R, F[_] : Functor](signature: Signature[T, R], arg: T)
                                (exec: String => F[String]): F[R] = {
    exec(encode(signature, arg)).map { s =>
      signature.out.decode(Hex.hexToBytes(s), 0).value
    }
  }

  def encode[T](signature: Signature[T, _], arg: T): String =
    signature.id + Hex.bytesToHex(signature.in.encode(arg))
}
