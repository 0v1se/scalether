package scalether.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.Address
import scalether.java.implicits._

class MonoSimpleNonceProvider(ethereum: MonoEthereum)
  extends SimpleNonceProvider[Mono](ethereum) with MonoNonceProvider {

  override def nonce(address: Address): Mono[BigInteger] = super.nonce(address)

  override def recover(address: Address): Mono[Unit] = super.recover(address)
}
