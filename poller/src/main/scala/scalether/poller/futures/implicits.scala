package scalether.poller.futures

import cats.implicits._
import scala.concurrent.ExecutionContext.Implicits.global
import scalether.poller.Poller
import scala.concurrent.Future

object implicits {
  implicit val futureSleeper: FutureSleeper = new FutureSleeper
  implicit val futurePoller: Poller[Future] = new Poller[Future](futureSleeper)
}
