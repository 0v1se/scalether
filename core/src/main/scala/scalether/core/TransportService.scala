package scalether.core

import scala.language.higherKinds

trait TransportService[F[_]] {
  def execute(request: String): F[String]
}
