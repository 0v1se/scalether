package scalether.extra.timer.future

import cats.implicits._
import scala.concurrent.ExecutionContext.Implicits.global

object implicits {
  implicit object FutureSleeper extends FutureSleeper
  implicit object FuturePoller extends FuturePoller
}
