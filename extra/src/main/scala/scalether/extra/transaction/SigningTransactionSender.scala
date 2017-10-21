package scalether.extra.transaction

import java.math.BigInteger

import cats.Monad
import cats.implicits._
import scalether.core.Ethereum
import scalether.domain.request.Transaction
import scalether.domain.{Address, Word}
import scalether.util.Hex

import scala.language.higherKinds

class SigningTransactionSender[F[_]](ethereum: Ethereum[F],
                                     nonceProvider: NonceProvider[F],
                                     from: Address,
                                     key: Word,
                                     gas: BigInteger,
                                     gasPrice: BigInteger)
                                    (implicit f: Monad[F])
  extends AbstractTransactionSender[F](ethereum, from, gas, gasPrice) {

  private val signer = new TransactionSigner(key)

  def sendTransaction(transaction: Transaction) = if (transaction.nonce != null) {
    ethereum.ethSendRawTransaction(Hex.prefixed(signer.sign(fill(transaction))))
  } else {
    nonceProvider.nonce(address = from).flatMap(
      nonce => ethereum.ethSendRawTransaction(Hex.prefixed(signer.sign(fill(transaction.copy(nonce = nonce)))))
    )
  }
}
