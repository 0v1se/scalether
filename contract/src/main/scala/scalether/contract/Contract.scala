package scalether.contract

import cats.Functor
import scalether.abi.{Call, Signature}
import scalether.core.Ethereum
import scalether.core.request.Transaction

import scala.language.higherKinds

class Contract[F[_]: Functor](val address: String, ethereum: Ethereum[F]) {
  def call[I, O](signature: Signature[I, O], in: I): F[O] =
    Call.call(signature, in)(data => ethereum.ethCall(Transaction(to = Some(address), data = Some(data))))
}
