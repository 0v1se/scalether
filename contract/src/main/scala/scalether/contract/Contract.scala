package scalether.contract

import cats.Functor
import scalether.abi.{Call, Signature}
import scalether.core.TransactionSender
import scalether.core.data.Address
import scalether.core.request.Transaction

import scala.language.higherKinds

abstract class Contract[F[_]](val address: Address, sender: TransactionSender[F])(implicit f: Functor[F]) {
  def sendTransaction[I](signature: Signature[I, _], in: I): F[String] =
    sender.sendTransaction(Transaction(to = Some(address), data = Some(Call.encode(signature, in))))

  def call[I, O](signature: Signature[I, O], in: I): F[O] =
    Call.call(signature, in)(data => sender.call(Transaction(to = Some(address), data = Some(data))))
}
