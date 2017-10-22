package scalether.extra.log

import java.math.BigInteger
import java.util.concurrent.CompletableFuture

import scalether.core.JavaEthereum
import scalether.domain.request.LogFilter
import scalether.java.implicits._

class JavaClientLogFilter(ethereum: JavaEthereum, logFilter: LogFilter, state: JavaLogFilterState)
  extends ClientLogFilter[CompletableFuture](ethereum, logFilter, state) {
  override def getChanges(blockNumber: BigInteger) = super.getChanges(blockNumber)
}
