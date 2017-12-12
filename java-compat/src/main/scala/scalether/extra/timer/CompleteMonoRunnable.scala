package scalether.extra.timer

import reactor.core.publisher.MonoSink

class CompleteMonoRunnable[T](sink: MonoSink[T], value: T)
  extends Runnable {

  def run(): Unit = sink.success(value)
}
