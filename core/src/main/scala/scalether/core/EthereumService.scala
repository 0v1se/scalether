package scalether.core

import cats.Functor
import cats.implicits._
import org.slf4j.{Logger, LoggerFactory}
import scalether.core.json.JsonConverter
import scalether.domain.{Request, Response}

import scala.language.higherKinds
import scala.reflect.Manifest
import scala.util.Try

class EthereumService[F[_]](service: TransportService[F], log: Boolean = false)(implicit f: Functor[F]) {
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
