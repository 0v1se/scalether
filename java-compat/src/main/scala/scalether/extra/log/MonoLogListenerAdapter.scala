package scalether.extra.log

import reactor.core.publisher.Mono
import scalether.domain.request.LogFilter
import scalether.domain.response.Log

class MonoLogListenerAdapter(listener: MonoLogListener) extends LogListener[Mono] {
  override def createFilter(fromBlock: String, toBlock: String): Mono[LogFilter] =
    listener.createFilter(fromBlock, toBlock)

  override def onLog(log: Log, confirmed: Boolean): Mono[Unit] =
    listener.onLog(log, confirmed).map(_ => ())
}
