package scalether.extra.transaction

import java.math.BigInteger

import cats.Monad
import cats.implicits._
import scalether.core.Ethereum
import scalether.core.request.Transaction
import scalether.domain.{Address, Word}
import scalether.util.Hex

import scala.language.higherKinds

class SigningTransactionSender[F[_]](ethereum: Ethereum[F], from: Address, key: Word, gas: BigInteger, gasPrice: BigInteger)
                                    (implicit f: Monad[F])
  extends AbstractTransactionSender[F](ethereum, from, gas, gasPrice) {

  private val signer = new TransactionSigner(key)

  def sendTransaction(transaction: Transaction) = if (transaction.nonce.isDefined) {
    ethereum.ethSendRawTransaction(Hex.prefixed(signer.sign(fill(transaction))))
  } else {
    ethereum.ethGetTransactionCount(from, "pending").flatMap(
      nonce => ethereum.ethSendRawTransaction(Hex.prefixed(signer.sign(fill(transaction.copy(nonce = Some(nonce))))))
    )
  }
}
