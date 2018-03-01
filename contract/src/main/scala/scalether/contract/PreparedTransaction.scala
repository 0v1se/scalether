package scalether.contract

import java.math.BigInteger

import cats.MonadError
import cats.implicits._
import scalether.abi.Signature
import scalether.domain.Address
import scalether.domain.request.Transaction
import scalether.transaction.TransactionSender

import scala.language.higherKinds

class PreparedTransaction[F[_], O](address: Address,
                                   signature: Signature[_, O],
                                   data: Array[Byte],
                                   sender: TransactionSender[F],
                                   value: BigInteger,
                                   gas: BigInteger,
                                   gasPrice: BigInteger)
                                  (implicit m: MonadError[F, Throwable]) {

  def withGas(newGas: BigInteger): PreparedTransaction[F, O] =
    new PreparedTransaction[F, O](address, signature, data, sender, value, newGas, gasPrice)

  def withGasPrice(newGasPrice: BigInteger): PreparedTransaction[F, O] =
    new PreparedTransaction[F, O](address, signature, data, sender, value, gas, newGasPrice)

  def withValue(newValue: BigInteger): PreparedTransaction[F, O] =
    new PreparedTransaction[F, O](address, signature, data, sender, newValue, gas, gasPrice)

  def call(): F[O] =
    sender.call(Transaction(to = address, data = data, value = value, gas = gas, gasPrice = gasPrice))
      .map(signature.decode)

  def execute(): F[String] =
    sender.sendTransaction(Transaction(to = address, data = data, value = value, gas = gas, gasPrice = gasPrice))

  def estimate(): F[BigInteger] =
    sender.estimate(Transaction(to = address, data = data, value = value, gas = gas, gasPrice = gasPrice))

  def estimateAndExecute(): F[String] =
    estimate().flatMap(estimated => this.withGas(estimated).execute())
}

object PreparedTransaction {
  def apply[F[_], I, O](address: Address,
                        signature: Signature[I, O],
                        in: I,
                        sender: TransactionSender[F],
                        value: BigInteger = null,
                        gas: BigInteger = null,
                        gasPrice: BigInteger = null)
                       (implicit m: MonadError[F, Throwable]): PreparedTransaction[F, O] =
    new PreparedTransaction[F, O](address, signature, signature.encode(in), sender, value, gas, gasPrice)
}