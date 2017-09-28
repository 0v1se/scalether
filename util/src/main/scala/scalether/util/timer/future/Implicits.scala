package scalether.util.timer.future

import cats.implicits._
import scala.concurrent.ExecutionContext.Implicits.global

object Implicits {

  implicit object FutureSleeper extends FutureSleeper

  implicit object FuturePoller extends FuturePoller

}
