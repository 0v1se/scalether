package scalether.extra.timer

import java.util.concurrent.CompletableFuture

class CompleteFutureRunnable[T](future: CompletableFuture[T], value: T)
  extends Runnable {

  def run() = future.complete(value)
}
