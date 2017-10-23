package scalether.extra.transaction

import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.Address

class MonoSimpleNonceProvider(ethereum: MonoEthereum) extends SimpleNonceProvider[Mono](ethereum) {
  override def nonce(address: Address) = super.nonce(address)
}
