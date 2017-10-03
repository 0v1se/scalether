package scalether.contract

import cats.Functor
import scalether.abi.{Call, Signature}
import scalether.core.TransactionSender
import scalether.core.request.Transaction

import scala.language.higherKinds

abstract class Contract[F[_] : Functor](val address: String, sender: TransactionSender[F]) {
  def sendTransaction[I](signature: Signature[I, _], in: I): F[String] = {
    val encoded = Call.encode(signature, in)
    sender.sendTransaction(Transaction(data => Some(encoded)))
  }

  def call[I, O](signature: Signature[I, O], in: I): F[O] =
    Call.call(signature, in)(data => sender.call(Transaction(to = Some(address), data = Some(data))))
}
