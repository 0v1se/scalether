package scalether.poller.mono

import scalether.java.implicits._
import reactor.core.publisher.Mono
import scalether.poller.Poller

object implicits {
  implicit val monoSleeper: MonoSleeper = new MonoSleeper
  implicit val monoPoller: Poller[Mono] = new Poller[Mono](monoSleeper)
}
