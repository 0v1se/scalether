package scalether.extra.log

import java.math.BigInteger

import reactor.core.publisher.Mono

trait MonoLogFilterState extends LogFilterState[Mono] {
  def getBlock: Mono[BigInteger]

  def setBlock(block: BigInteger): Mono[Unit]
}
