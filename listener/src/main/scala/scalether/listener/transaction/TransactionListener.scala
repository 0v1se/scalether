package scalether.listener.transaction

import java.math.BigInteger

import scala.language.higherKinds

trait TransactionListener[F[_]] {
  def enabled: Boolean
  def onTransaction(transactionHash: String, blockHash: String, blockNumber: BigInteger, confirmations: Int, confirmed: Boolean): F[Unit]
}
