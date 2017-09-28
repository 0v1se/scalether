package scalether.util.timer.future

import cats.Monad
import scalether.util.timer.{Poller, Sleeper}

import scala.concurrent.Future

class FuturePoller(implicit sleeper: Sleeper[Future], m: Monad[Future]) extends Poller[Future] {

}
