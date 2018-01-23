package scalether.extra.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.domain.Address
import scalether.transaction.NonceProvider

trait MonoNonceProvider extends NonceProvider[Mono] {
  def nonce(address: Address): Mono[BigInteger]
}
