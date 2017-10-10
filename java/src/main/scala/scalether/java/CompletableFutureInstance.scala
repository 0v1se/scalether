package scalether.java

import java.util.concurrent.CompletableFuture

import cats.MonadError

import scala.util.{Failure, Success, Try}

class CompletableFutureInstance extends MonadError[CompletableFuture, Throwable] {

  override def map[A, B](fa: CompletableFuture[A])(f: A => B) =
    fa.thenApply(a => f(a))

  def flatMap[A, B](fa: CompletableFuture[A])(f: A => CompletableFuture[B]) =
    fa.thenCompose(a => f(a))

  def tailRecM[A, B](a: A)(f: A => CompletableFuture[Either[A, B]]) =
    ???

  def raiseError[A](e: Throwable) = {
    val result = new CompletableFuture[A]()
    result.completeExceptionally(e)
    result
  }

  def handleErrorWith[A](fa: CompletableFuture[A])(f: Throwable => CompletableFuture[A]) =
    fa.handle[Try[A]]((a, ex) => if (ex == null) Failure(ex) else Success(a))
      .thenCompose[A] {
      case Success(a) => CompletableFuture.completedFuture(a)
      case Failure(ex) => f(ex)
    }

  def pure[A](x: A) = CompletableFuture.completedFuture(x)
}
