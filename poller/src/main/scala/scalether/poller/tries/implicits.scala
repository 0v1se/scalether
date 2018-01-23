package scalether.poller.tries

import cats.implicits._
import scalether.poller.Poller

import scala.util.Try

object implicits {
  implicit val trySleeper: TrySleeper = new TrySleeper
  implicit val tryPoller: Poller[Try] = new Poller[Try](trySleeper)
}
