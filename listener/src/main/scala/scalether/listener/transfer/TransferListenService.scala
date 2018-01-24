package scalether.listener.transfer

import scala.language.higherKinds

abstract class TransferListenService[F[_]] {
  def check(): F[Unit]
}
