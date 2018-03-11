package scalether.listener.transaction

import scala.language.higherKinds

trait TransactionListener[F[_]] {
  def enabled: Boolean
  def onTransaction(txHash: String, confirmations: Int, confirmed: Boolean): F[Unit]
}
