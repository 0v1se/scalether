package scalether.util.timer

import cats.Monad
import cats.implicits._

class Poller[F[_]](implicit sleeper: Sleeper[F], m: Monad[F]) {
  def poll[T](sleep: Long)(poller: => F[Option[T]]): F[T] =
    poller flatMap (_.map(m.pure).getOrElse(poll(sleep)(poller)))
}
