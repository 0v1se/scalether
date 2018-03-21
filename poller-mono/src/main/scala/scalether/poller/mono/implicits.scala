package scalether.poller.mono

import io.daonomic.cats.mono.implicits._
import reactor.core.publisher.Mono
import scalether.poller.{MonoSleeper, Poller}

object implicits {
  implicit val monoSleeper: MonoSleeper = new MonoSleeper
  implicit val monoPoller: Poller[Mono] = new Poller[Mono](monoSleeper)
}
