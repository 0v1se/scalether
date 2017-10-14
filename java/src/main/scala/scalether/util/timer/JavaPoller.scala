package scalether.util.timer

import java.util.concurrent.CompletableFuture

import scalether.java.implicits._
import scalether.util.timer.implicits._

class JavaPoller extends Poller[CompletableFuture]{
  override def poll[T](sleep: Long)(poller: => CompletableFuture[Option[T]]) = super.poll(sleep)(poller)
}
