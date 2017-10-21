package scalether.extra.transaction

import java.util.concurrent.CompletableFuture

import scalether.core.JavaEthereum
import scalether.domain.Address

class JavaSimpleNonceProvider(ethereum: JavaEthereum) extends SimpleNonceProvider[CompletableFuture](ethereum) {
  override def nonce(address: Address) = super.nonce(address)
}
