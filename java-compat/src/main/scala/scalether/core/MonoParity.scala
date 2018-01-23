package scalether.core

import java.util

import reactor.core.publisher.Mono
import scalether.domain.response.parity.Trace
import scalether.java.implicits._

import scala.collection.JavaConverters._

class MonoParity(monoEthereumService: MonoEthereumService) extends Parity[Mono](monoEthereumService) {
  override def traceTransaction(txHash: String): Mono[List[Trace]] =
    super.traceTransaction(txHash)

  def traceTransactionJava(txHash: String): Mono[util.List[Trace]] =
    traceTransaction(txHash).map(_.asJava)
}
