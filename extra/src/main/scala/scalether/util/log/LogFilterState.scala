package scalether.util.log

import java.math.BigInteger

import scala.language.higherKinds

trait LogFilterState[F[_]] {
  def getBlock: F[BigInteger]
  def setBlock(block: BigInteger): F[Unit]
}