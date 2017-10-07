package scalether.core

import cats.Functor
import cats.implicits._
import scalether.core.json.JsonConverter

import scala.language.higherKinds
import scala.reflect.Manifest

class EthereumServiceImpl[F[_]](service: TransportService[F], log: Boolean = false)(implicit f: Functor[F]) extends EthereumService[F] {
  val json = new JsonConverter

  def execute[T](request: Request)(implicit mf: Manifest[T]): F[Response[T]] = {
    val requestJson = json.toJson(request)
    if (log) {
      println(s"request=$requestJson")
    }
    service.execute(requestJson).map(responseJson => {
      if (log) {
        println(s"response=$responseJson")
      }
      json.fromJson[Response[T]](responseJson)
    })
  }
}
