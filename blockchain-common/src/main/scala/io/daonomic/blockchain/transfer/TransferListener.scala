package io.daonomic.blockchain.transfer

import java.math.BigInteger

import scala.language.higherKinds

trait TransferListener[F[_]] {
  def onTransfer(transfer: Transfer, confirmations: Int, confirmed: Boolean): F[Unit]
}
