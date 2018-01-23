package scalether.extra.log

import scalether.java.implicits._
import java.math.BigInteger
import java.util

import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.response.Log

import scala.collection.JavaConverters._

class MonoLogListenServiceAdapter(ethereum: MonoEthereum, confidence: Int, listener: MonoLogListener, monoState: MonoState[BigInteger]) {
  val scala = new LogListenService[Mono](ethereum, confidence, new MonoLogListenerAdapter(listener), new MonoStateAdapter[BigInteger](monoState))

  def check(): Mono[util.List[Log]] = scala.check().map(scalaList => scalaList.asJava)
}
