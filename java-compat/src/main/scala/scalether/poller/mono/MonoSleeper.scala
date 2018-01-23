package scalether.poller.mono

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}

import reactor.core.publisher.Mono
import scalether.poller.Sleeper

class MonoSleeper extends Sleeper[Mono] {
  val executor: ScheduledExecutorService = Executors.newScheduledThreadPool(1)

  def sleep(sleep: Long): Mono[Unit] =
    Mono.create[Unit](sink => {
      executor.schedule(new CompleteMonoRunnable(sink, ()), sleep, TimeUnit.MILLISECONDS)
    })
}
