package scalether.transport

import scalether.core.TransportService

import scala.util.Try
import scalaj.http.Http

class ScalajHttpTransportService(rpcUrl: String) extends TransportService[Try] {
  override def execute(request: String) = Try {
    Http(rpcUrl)
      .method("POST")
      .postData(request)
      .asString.body
  }
}
