package scalether.listener.log

import java.math.BigInteger
import java.util

import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.response.Log
import scalether.java.implicits._
import scalether.listener.common.{MonoState, MonoStateAdapter}

import scala.collection.JavaConverters._

class MonoLogListenService(ethereum: MonoEthereum, confidence: Int, listener: MonoLogListener, monoState: MonoState[BigInteger]) {
  private val scala = new LogListenService[Mono](ethereum, confidence, new MonoLogListenerAdapter(listener), new MonoStateAdapter[BigInteger](monoState))

  def check(blockNumber: BigInteger): Mono[util.List[Log]] =
    scala.check(blockNumber).map(scalaList => scalaList.asJava)
}
