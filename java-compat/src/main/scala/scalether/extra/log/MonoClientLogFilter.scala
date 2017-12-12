package scalether.extra.log

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.request.LogFilter
import scalether.domain.response.Log
import scalether.java.implicits._

class MonoClientLogFilter(ethereum: MonoEthereum, logFilter: LogFilter, state: MonoLogFilterState)
  extends ClientLogFilter[Mono](ethereum, logFilter, state) {
  override def getChanges(blockNumber: BigInteger): Mono[List[Log]] = super.getChanges(blockNumber)
}
