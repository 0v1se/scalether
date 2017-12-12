package scalether.java

import cats.MonadError
import reactor.core.publisher.Mono

class MonoInstance extends MonadError[Mono, Throwable] {
  def flatMap[A, B](fa: Mono[A])(f: A => Mono[B]): Mono[B] =
    fa.flatMap(a => f(a))

  def tailRecM[A, B](a: A)(f: A => Mono[Either[A, B]]): Mono[B] =
    ???

  def raiseError[A](e: Throwable): Mono[A] =
    Mono.error(e)

  def handleErrorWith[A](fa: Mono[A])(f: Throwable => Mono[A]): Mono[A] =
    fa.doOnError(e => f(e))

  def pure[A](x: A): Mono[A] =
    Mono.just(x)
}
