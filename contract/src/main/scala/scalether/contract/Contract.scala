package scalether.contract

import cats.Functor
import scalether.abi.{Call, Signature}
import scalether.core.{Ethereum, Transaction}

import scala.language.higherKinds

class Contract[F[_]: Functor](val address: String, ethereum: Ethereum[F]) {
  def call[I, O](signature: Signature[I, O], in: I): F[O] =
    Call.call(signature, in)(data => ethereum.ethCall(Transaction(to = address, data = Some(data))))
}
