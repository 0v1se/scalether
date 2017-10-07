package scalether.util.timer.tries

import cats.implicits._

object Implicits {
  implicit object TrySleeper extends TrySleeper
  implicit object TryPoller extends TryPoller
}
