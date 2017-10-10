package scalether.core

import java.util.concurrent.CompletableFuture

import scalether.java.implicits._
import scalether.transport.JavaTransportService

class JavaEthereumService(service: JavaTransportService, log: Boolean = false) extends EthereumService[CompletableFuture](service, log) {

}
