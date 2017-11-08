package scalether.transport

import scalether.core.TransportService

import scala.util.Try
import scalaj.http.Http

class ScalajHttpTransportService(rpcUrl: String, connTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000) extends TransportService[Try] {
  private val requestTemplate = Http(rpcUrl)
    .timeout(connTimeoutMs, readTimeoutMs)
    .header("Content-Type", "application/json")

  override def execute(request: String) = Try {
    val response = requestTemplate
      .postData(request)
      .asString
    response.body
  }
}
