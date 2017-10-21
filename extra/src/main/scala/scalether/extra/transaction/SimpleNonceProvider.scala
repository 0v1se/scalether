package scalether.extra.transaction

import scalether.core.Ethereum
import scalether.domain.Address

import scala.language.higherKinds

class SimpleNonceProvider[F[_]](ethereum: Ethereum[F])

  extends NonceProvider[F] {
  def nonce(address: Address) = ethereum.ethGetTransactionCount(address, "pending")
}
