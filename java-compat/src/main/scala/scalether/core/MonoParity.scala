package scalether.core

import java.math.BigInteger
import java.util

import reactor.core.publisher.Mono
import scalether.domain.response.parity.Trace
import scalether.java.implicits._

import scala.collection.JavaConverters._

class MonoParity(monoEthereumService: MonoEthereumService) extends Parity[Mono](monoEthereumService) {
  override def traceTransaction(txHash: String): Mono[List[Trace]] =
    super.traceTransaction(txHash)

  override def traceBlock(blockNumber: BigInteger): Mono[List[Trace]] =
    super.traceBlock(blockNumber)

  def traceTransactionJava(txHash: String): Mono[util.List[Trace]] =
    traceTransaction(txHash).map(_.asJava)

  def traceBlockJava(blockNumber: BigInteger): Mono[util.List[Trace]] =
    traceBlock(blockNumber).map(_.asJava)

}
