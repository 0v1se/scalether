package scalether.log

import reactor.core.publisher.Mono
import scalether.domain.request.LogFilter
import scalether.domain.response.Log
import scalether.extra.log.MonoLogListener
import scalether.listener.log.LogListener

class MonoLogListenerAdapter(listener: MonoLogListener) extends LogListener[Mono] {
  override def createFilter(fromBlock: String, toBlock: String): Mono[LogFilter] =
    listener.createFilter(fromBlock, toBlock)

  override def onLog(log: Log, confirmations: Int, confirmed: Boolean): Mono[Unit] =
    listener.onLog(log, confirmations, confirmed)
      .map[Unit](_ => ())
      .switchIfEmpty(Mono.just())

  override def enabled: Boolean = listener.isEnabled
}
