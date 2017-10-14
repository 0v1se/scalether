package scalether.extra.timer

import cats.Monad
import cats.implicits._

import scala.language.higherKinds

class Poller[F[_]](implicit sleeper: Sleeper[F], m: Monad[F]) {
  def poll[T](sleep: Long)(poller: => F[Option[T]]): F[T] =
    poller flatMap (_.map(m.pure).getOrElse(poll(sleep)(poller)))
}
