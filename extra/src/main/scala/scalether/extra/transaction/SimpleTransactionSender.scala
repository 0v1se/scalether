package scalether.extra.transaction

import java.math.BigInteger

import scalether.core.Ethereum
import scalether.core.request.Transaction
import scalether.domain.Address

import scala.language.higherKinds

class SimpleTransactionSender[F[_]](ethereum: Ethereum[F], from: Address, gas: BigInteger, gasPrice: BigInteger)
  extends AbstractTransactionSender[F](ethereum, from, gas, gasPrice) {

  def sendTransaction(transaction: Transaction) =
    ethereum.ethSendTransaction(fill(transaction))
}
