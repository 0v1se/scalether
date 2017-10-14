package scalether.extra.timer.tries

import scalether.extra.timer.Sleeper

import scala.util.Try

class TrySleeper extends Sleeper[Try] {
  def sleep(sleep: Long) = Try {
    Thread.sleep(sleep)
  }
}
