package scalether.extra.timer.tries

import cats.Monad
import scalether.extra.timer.{Poller, Sleeper}

import scala.util.Try

class TryPoller(implicit sleeper: Sleeper[Try], m: Monad[Try]) extends Poller[Try] {

}
