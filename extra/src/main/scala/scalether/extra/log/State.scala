package scalether.extra.log

import scala.language.higherKinds

trait State[T, F[_]] {
  def get: F[Option[T]]
  def set(value: T): F[Unit]
}
