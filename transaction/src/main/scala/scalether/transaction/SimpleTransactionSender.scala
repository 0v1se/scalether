package scalether.transaction

import java.math.BigInteger

import cats.Monad
import cats.implicits._
import scalether.core.Ethereum
import scalether.domain.Address
import scalether.domain.request.Transaction

import scala.language.higherKinds

class SimpleTransactionSender[F[_]](ethereum: Ethereum[F], from: Address, gas: BigInteger, gasPrice: GasPriceProvider[F])
                                   (implicit m: Monad[F])
  extends AbstractTransactionSender[F](ethereum, from, gas, gasPrice) {

  def sendTransaction(transaction: Transaction): F[String] = fill(transaction).flatMap {
    t => ethereum.ethSendTransaction(t)
  }
}
