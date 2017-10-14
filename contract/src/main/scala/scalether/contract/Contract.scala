package scalether.contract

import cats.Functor
import scalether.abi.{Call, Signature}
import scalether.core.request.Transaction
import scalether.domain.Address
import scalether.extra.transaction.TransactionSender

import scala.language.higherKinds

abstract class Contract[F[_]](val address: Address, sender: TransactionSender[F])(implicit f: Functor[F]) {
  def sendTransaction[I](signature: Signature[I, _], in: I): F[String] =
    sender.sendTransaction(Transaction(to = Some(address), data = Call.encode(signature, in)))

  def call[I, O](signature: Signature[I, O], in: I): F[O] =
    Call.call(signature, in)(data => sender.call(Transaction(to = Some(address), data = data)))
}
