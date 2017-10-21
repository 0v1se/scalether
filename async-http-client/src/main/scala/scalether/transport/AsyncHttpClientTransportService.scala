package scalether.transport

import org.asynchttpclient.{DefaultAsyncHttpClient, RequestBuilder}
import scalether.core.TransportService

import scala.compat.java8.FutureConverters
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AsyncHttpClientTransportService(rpcUrl: String, requestTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000) extends TransportService[Future] {
  private val client = new DefaultAsyncHttpClient()

  override def execute(request: String) = {
    val req = new RequestBuilder()
      .setReadTimeout(readTimeoutMs)
      .setRequestTimeout(requestTimeoutMs)
      .setUrl(rpcUrl)
      .setBody(request)
      .setMethod("POST")
    FutureConverters.toScala(client.executeRequest(req).toCompletableFuture)
      .map(_.getResponseBody)
  }
}
