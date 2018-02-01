package scalether.contract

import java.math.BigInteger

import cats.implicits._
import cats.MonadError
import scalether.abi.{Call, Signature}
import scalether.domain.Address
import scalether.domain.request.Transaction
import scalether.transaction.TransactionSender

import scala.language.higherKinds

class PreparedTransaction[F[_], I, O](address: Address,
                                      signature: Signature[I, O],
                                      in: I,
                                      sender: TransactionSender[F],
                                      value: BigInteger = null,
                                      gas: BigInteger = null,
                                      gasPrice: BigInteger = null)
                                     (implicit m: MonadError[F, Throwable]) {

  def withGas(newGas: BigInteger): PreparedTransaction[F, I, O] =
    new PreparedTransaction[F, I, O](address, signature, in, sender, value, newGas, gasPrice)

  def withGasPrice(newGasPrice: BigInteger): PreparedTransaction[F, I, O] =
    new PreparedTransaction[F, I, O](address, signature, in, sender, value, gas, newGasPrice)

  def call(): F[O] =
    Call.call(signature, in)(data => sender.call(Transaction(to = address, data = data, value = value, gas = gas, gasPrice = gasPrice)))

  def execute(): F[String] =
    sender.sendTransaction(Transaction(to = address, data = Call.encode(signature, in), value = value, gas = gas, gasPrice = gasPrice))

  def estimate(): F[BigInteger] =
    sender.estimate(Transaction(to = address, data = Call.encode(signature, in), value = value, gas = gas, gasPrice = gasPrice))

  def estimateAndExecute(): F[String] =
    estimate().flatMap(estimated => this.withGas(estimated).execute())

  def estimateAndExecute(maxGas: BigInteger): F[String] =
    estimate().flatMap {
      case estimated if estimated.compareTo(maxGas) <= 0 => withGas(estimated).execute()
      case estimated => m.raiseError(new RuntimeException(s"transaction required more than $maxGas gas: $estimated"))
    }
}
