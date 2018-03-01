package scalether.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.abi.Signature
import scalether.contract.PreparedTransaction
import scalether.domain.Address
import scalether.java.implicits._

class MonoPreparedTransaction[O](address: Address,
                                 signature: Signature[_, O],
                                 data: Array[Byte],
                                 sender: MonoTransactionSender,
                                 value: BigInteger,
                                 gas: BigInteger,
                                 gasPrice: BigInteger)
  extends PreparedTransaction[Mono, O](address, signature, data, sender, value, gas, gasPrice) {

  override def withGas(newGas: BigInteger): MonoPreparedTransaction[O] =
    new MonoPreparedTransaction[O](address, signature, data, sender, value, newGas, gasPrice)

  override def withGasPrice(newGasPrice: BigInteger): MonoPreparedTransaction[O] =
    new MonoPreparedTransaction[O](address, signature, data, sender, value, gas, newGasPrice)

  override def withValue(newValue: BigInteger): MonoPreparedTransaction[O] =
    new MonoPreparedTransaction[O](address, signature, data, sender, newValue, gas, gasPrice)

  override def call(): Mono[O] = super.call()

  override def execute(): Mono[String] = super.execute()

  override def estimate(): Mono[BigInteger] = super.estimate()

  override def estimateAndExecute(): Mono[String] = super.estimateAndExecute()
}

object MonoPreparedTransaction {
  def apply[I, O](address: Address,
                  signature: Signature[I, O],
                  in: I,
                  sender: MonoTransactionSender,
                  value: BigInteger = null,
                  gas: BigInteger = null,
                  gasPrice: BigInteger = null): MonoPreparedTransaction[O] =
    new MonoPreparedTransaction[O](address, signature, signature.encode(in), sender, value, gas, gasPrice)
}