package scalether.extra.log

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.request.LogFilter
import scalether.java.implicits._

class MonoClientLogFilter(ethereum: MonoEthereum, logFilter: LogFilter, state: MonoLogFilterState)
  extends ClientLogFilter[Mono](ethereum, logFilter, state) {
  override def getChanges(blockNumber: BigInteger) = super.getChanges(blockNumber)
}
