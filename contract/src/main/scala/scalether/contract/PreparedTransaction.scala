package scalether.contract

import java.math.BigInteger

import cats.Functor
import scalether.abi.{Call, Signature}
import scalether.domain.Address
import scalether.domain.request.Transaction
import scalether.extra.transaction.TransactionSender

import scala.language.higherKinds

class PreparedTransaction[F[_] : Functor, I, O](address: Address,
                                                signature: Signature[I, O],
                                                in: I,
                                                sender: TransactionSender[F],
                                                value: BigInteger = null) {
  def call(): F[O] =
    Call.call(signature, in)(data => sender.call(Transaction(to = address, data = data)))

  def execute(): F[String] =
    sender.sendTransaction(Transaction(to = address, data = Call.encode(signature, in), value = value))

  def estimate(): F[BigInteger] =
    sender.estimate(Transaction(to = address, data = Call.encode(signature, in), value = value))
}
