package scalether.extra.timer.future

import cats.Monad
import scalether.extra.timer.{Poller, Sleeper}

import scala.concurrent.Future

class FuturePoller(implicit sleeper: Sleeper[Future], m: Monad[Future]) extends Poller[Future] {

}
