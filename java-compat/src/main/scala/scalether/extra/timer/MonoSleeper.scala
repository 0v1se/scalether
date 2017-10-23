package scalether.extra.timer

import java.util.concurrent.{Executors, TimeUnit}

import reactor.core.publisher.Mono

class MonoSleeper extends Sleeper[Mono] {
  val executor = Executors.newScheduledThreadPool(1)

  def sleep(sleep: Long) = {
    val mono: Mono[Unit] = Mono.create(sink => {
      executor.schedule(new CompleteMonoRunnable(sink, ()), sleep, TimeUnit.MILLISECONDS)
    })
    mono
  }
}
