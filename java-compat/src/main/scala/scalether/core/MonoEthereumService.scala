package scalether.core

import reactor.core.publisher.Mono
import scalether.domain.{Request, Response}
import scalether.java.implicits._
import scalether.transport.MonoTransportService

class MonoEthereumService(service: MonoTransportService, log: Boolean = false) extends EthereumService[Mono](service, log) {
  override def execute[T](request: Request)(implicit mf: Manifest[T]): Mono[Response[T]] = super.execute(request)
}
