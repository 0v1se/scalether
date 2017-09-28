package scalether.core

import scala.language.higherKinds
import scala.reflect.Manifest

trait EthereumService[F[_]] {
  def execute[T](request: Request)(implicit mf: Manifest[T]): F[Response[T]]
}
