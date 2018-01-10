package scalether.contract

import java.math.BigInteger

import cats.Functor
import scalether.abi.{Call, Signature}
import scalether.domain.Address
import scalether.domain.request.Transaction
import scalether.extra.transaction.TransactionSender

import scala.language.higherKinds

abstract class Contract[F[_]](val address: Address, sender: TransactionSender[F])(implicit f: Functor[F]) {
  def sendTransaction[I](signature: Signature[I, _], in: I, value: BigInteger = null): F[String] =
    sender.sendTransaction(Transaction(to = address, data = Call.encode(signature, in), value = value))

  def call[I, O](signature: Signature[I, O], in: I): F[O] =
    Call.call(signature, in)(data => sender.call(Transaction(to = address, data = data)))
}
