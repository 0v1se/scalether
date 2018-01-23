package scalether.poller.tries

import scalether.poller.Sleeper

import scala.util.Try

class TrySleeper extends Sleeper[Try] {
  def sleep(sleep: Long) = Try {
    Thread.sleep(sleep)
  }
}
