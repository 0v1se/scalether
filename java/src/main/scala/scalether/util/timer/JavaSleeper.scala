package scalether.util.timer

import java.util.concurrent.{CompletableFuture, Executors, TimeUnit}

class JavaSleeper extends Sleeper[CompletableFuture] {
  val executor = Executors.newScheduledThreadPool(1)

  def sleep(sleep: Long) = {
    val future = new CompletableFuture[Unit]()
    executor.schedule(new CompleteFutureRunnable(future, ()), sleep, TimeUnit.MILLISECONDS)
    future
  }
}
