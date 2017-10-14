package scalether.util.log

import java.math.BigInteger
import java.util.concurrent.CompletableFuture

import scalether.extra.log.LogFilterState

trait JavaLogFilterState extends LogFilterState[CompletableFuture] {
  def getBlock: CompletableFuture[BigInteger]

  def setBlock(block: BigInteger): CompletableFuture[Unit]
}
