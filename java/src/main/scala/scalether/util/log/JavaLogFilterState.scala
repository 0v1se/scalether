package scalether.util.log

import java.math.BigInteger
import java.util.concurrent.CompletableFuture

trait JavaLogFilterState extends LogFilterState[CompletableFuture] {
  def getBlock: CompletableFuture[BigInteger]

  def setBlock(block: BigInteger): CompletableFuture[Unit]
}
