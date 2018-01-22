package scalether.extra.log

import cats.Monad

import scala.language.higherKinds

class SimpleState[T, F[_]](initial: Option[T])
                          (implicit m: Monad[F]) extends State[T, F] {

  var value: Option[T] = initial

  override def get: F[Option[T]] = m.pure(value)

  override def set(value: T): F[Unit] = {
    this.value = Some(value)
    m.unit
  }
}
