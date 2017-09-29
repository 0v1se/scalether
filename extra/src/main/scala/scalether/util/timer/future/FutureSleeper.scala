package scalether.util.timer.future

import java.util.concurrent.{Executors, TimeUnit}

import scalether.util.timer.Sleeper

import scala.concurrent.{Future, Promise}

class FutureSleeper extends Sleeper[Future] {
  private val executor = Executors.newScheduledThreadPool(1)

  def sleep(sleep: Long) = {
    val promise = Promise[Unit]()
    executor.schedule(new CompletePromiseRunnable(promise, ()), sleep, TimeUnit.MILLISECONDS)
    promise.future
  }
}
