package scalether.extra.timer

import reactor.core.publisher.Mono
import scalether.java.implicits._
import scalether.extra.timer.implicits._

class MonoPoller extends Poller[Mono]{
  override def poll[T](sleep: Long)(poller: => Mono[Option[T]]) = super.poll(sleep)(poller)
}
