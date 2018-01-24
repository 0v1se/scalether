package scalether.listener.transfer

import scala.language.higherKinds

trait TransferListener[F[_]] {
  def enabled: Boolean
  def onTransfer(transfer: Transfer, confirmations: Int, confirmed: Boolean): F[Unit]
}
