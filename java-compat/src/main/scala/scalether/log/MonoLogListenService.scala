package scalether.log

import java.math.BigInteger
import java.util

import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.response.Log
import scalether.extra.log.{MonoLogListener, MonoState}
import scalether.java.implicits._
import scalether.listener.log.LogListenService

import scala.collection.JavaConverters._

class MonoLogListenService(ethereum: MonoEthereum, confidence: Int, listener: MonoLogListener, monoState: MonoState[BigInteger]) {
  val scala = new LogListenService[Mono](ethereum, confidence, new MonoLogListenerAdapter(listener), new MonoStateAdapter[BigInteger](monoState))

  def check(blockNumber: BigInteger): Mono[util.List[Log]] =
    scala.check(blockNumber).map(scalaList => scalaList.asJava)
}
