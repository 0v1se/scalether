package scalether.extra.log

import scalether.domain.response.Log

import scala.language.higherKinds

trait LogListener[F[_]] {
  def createFilter(fromBlock: String, toBlock: String)
  def onLog(log: Log, confirmed: Boolean)
}
