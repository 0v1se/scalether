package scalether.util.log

import scalether.java.implicits._
import java.util.concurrent.CompletableFuture

import scalether.core.JavaEthereum
import scalether.core.request.LogFilter
import scalether.extra.log.ClientLogFilter

class JavaClientLogFilter(ethereum: JavaEthereum, logFilter: LogFilter, state: JavaLogFilterState)
  extends ClientLogFilter[CompletableFuture](ethereum, logFilter, state) {
  override def getChanges = super.getChanges
}
