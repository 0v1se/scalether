package scalether.util.timer.tries

import cats.implicits._

object implicits {
  implicit object TrySleeper extends TrySleeper
  implicit object TryPoller extends TryPoller
}
