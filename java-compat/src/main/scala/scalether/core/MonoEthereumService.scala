package scalether.core

import reactor.core.publisher.Mono
import scalether.java.implicits._
import scalether.transport.MonoTransportService

class MonoEthereumService(service: MonoTransportService, log: Boolean = false) extends EthereumService[Mono](service, log) {

}
