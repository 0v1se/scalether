package scalether.core

import cats.Functor
import cats.implicits._
import scalether.core.json.JsonConverter

import scala.language.higherKinds
import scala.reflect.Manifest

class EthereumServiceImpl[F[_]](service: TransportService[F])(implicit f: Functor[F]) extends EthereumService[F] {
  val json = new JsonConverter

  def execute[T](request: Request)(implicit mf: Manifest[T]): F[Response[T]] = {
    service.execute(json.toJson(request)).map(json.fromJson[Response[T]])
  }
}
