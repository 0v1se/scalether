package scalether.util.timer.tries

import scalether.util.timer.Sleeper

import scala.util.Try

class TrySleeper extends Sleeper[Try] {
  def sleep(sleep: Long) = Try {
    Thread.sleep(sleep)
  }
}
