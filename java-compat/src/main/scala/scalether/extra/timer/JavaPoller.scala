package scalether.extra.timer

import java.util.concurrent.CompletableFuture

import scalether.extra.timer.implicits._
import scalether.java.implicits._

class JavaPoller extends Poller[CompletableFuture]{
  override def poll[T](sleep: Long)(poller: => CompletableFuture[Option[T]]) = super.poll(sleep)(poller)
}
