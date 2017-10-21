package scalether.extra.transaction

import java.math.BigInteger
import java.util.concurrent.CompletableFuture

import scalether.domain.Address

trait JavaNonceProvider extends NonceProvider[CompletableFuture] {
  def nonce(address: Address): CompletableFuture[BigInteger]
}
