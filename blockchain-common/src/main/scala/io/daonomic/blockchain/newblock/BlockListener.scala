package io.daonomic.blockchain.newblock

import java.math.BigInteger

import scala.language.higherKinds

trait BlockListener[F[_]] {
  def onBlock(block: BigInteger): F[Unit]
}
