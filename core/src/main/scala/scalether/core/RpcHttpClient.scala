package scalether.core

import cats.MonadError
import cats.implicits._
import scalether.domain.{Error, Request}

import scala.language.higherKinds

abstract class RpcHttpClient[F[_]](service: EthereumService[F])
                                  (implicit me: MonadError[F, Throwable]) {

  protected def exec[T](method: String, params: Any*)
                     (implicit mf: Manifest[T]): F[T] = {
    execOption[T](method, params: _*).flatMap {
      case Some(v) => me.pure(v)
      case None => me.raiseError(new RpcException(Error.default))
    }
  }

  protected def execOption[T](method: String, params: Any*)
                           (implicit mf: Manifest[T]): F[Option[T]] = {
    service.execute[T](Request(1, method, params: _*)).flatMap {
      response =>
        response.error match {
          case Some(r) => me.raiseError(new RpcException(r))
          case None => me.pure(response.result)
        }
    }
  }
}
