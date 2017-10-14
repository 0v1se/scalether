package scalether.extra.timer.future

import scala.concurrent.Promise

class CompletePromiseRunnable[T](promise: Promise[T], value: T)
  extends Runnable {

  def run() = promise.success(value)
}
