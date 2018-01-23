package scalether.log

import scalether.domain.request.LogFilter
import scalether.domain.response.Log

import scala.language.higherKinds

trait LogListener[F[_]] {
  def createFilter(fromBlock: String, toBlock: String): F[LogFilter]
  def onLog(log: Log, confirmed: Boolean): F[Unit]
}
