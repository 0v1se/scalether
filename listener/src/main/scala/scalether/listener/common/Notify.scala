package scalether.listener.common

import cats.implicits._
import cats.Monad

import scala.language.higherKinds

object Notify {
  def every[T, F[_]](list: List[T])(f: T => F[Unit])(implicit m: Monad[F]): F[Unit] = {
    list.foldLeft(m.unit)((monad, next) => monad.flatMap(_ => f(next)))
  }
}
