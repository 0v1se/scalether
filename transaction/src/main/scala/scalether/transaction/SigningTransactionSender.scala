package scalether.transaction

import java.math.BigInteger

import cats.Monad
import cats.implicits._
import org.web3j.crypto.Keys
import scalether.core.Ethereum
import scalether.domain.Address
import scalether.domain.request.Transaction
import scalether.util.Hex

import scala.language.higherKinds

class SigningTransactionSender[F[_]](ethereum: Ethereum[F],
                                     nonceProvider: NonceProvider[F],
                                     privateKey: BigInteger,
                                     gas: BigInteger,
                                     gasPrice: GasPriceProvider[F])
                                    (implicit f: Monad[F])
  extends AbstractTransactionSender[F](ethereum, Address.apply(Keys.getAddressFromPrivateKey(privateKey)), gas, gasPrice) {

  private val signer = new TransactionSigner(privateKey)

  def sendTransaction(transaction: Transaction): F[String] = fill(transaction).flatMap {
    transaction =>
      if (transaction.nonce != null) {
        ethereum.ethSendRawTransaction(Hex.prefixed(signer.sign(transaction)))
      } else {
        nonceProvider.nonce(address = from).flatMap(
          nonce => ethereum.ethSendRawTransaction(Hex.prefixed(signer.sign(transaction.copy(nonce = nonce))))
        )
      }
  }
}
