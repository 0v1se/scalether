package scalether.util.timer.tries

import cats.Monad
import scalether.util.timer.{Poller, Sleeper}

import scala.util.Try

class TryPoller(implicit sleeper: Sleeper[Try], m: Monad[Try]) extends Poller[Try] {

}
