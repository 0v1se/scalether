package scalether.extra.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.abi.Signature
import scalether.contract.PreparedTransaction
import scalether.domain.Address
import scalether.java.implicits._

class MonoPreparedTransaction[I, O](address: Address,
                                    signature: Signature[I, O],
                                    in: I,
                                    sender: MonoTransactionSender,
                                    value: BigInteger = null)
  extends PreparedTransaction[Mono, I, O](address, signature, in, sender, value) {

  override def call(): Mono[O] = super.call()

  override def execute(): Mono[String] = super.execute()

  override def estimate(): Mono[BigInteger] = super.estimate()
}
