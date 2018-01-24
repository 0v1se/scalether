package scalether.listener.block

import java.math.BigInteger

import scala.language.higherKinds

trait BlockListener[F[_]] {
  def enabled: Boolean
  def onBlock(block: BigInteger): F[Unit]
}
