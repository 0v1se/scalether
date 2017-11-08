package scalether.transport

import org.asynchttpclient.{DefaultAsyncHttpClient, RequestBuilder}
import reactor.core.publisher.Mono
import scalether.core.TransportService

class MonoTransportService(rpcUrl: String, requestTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000)
  extends TransportService[Mono] {

  private val client = new DefaultAsyncHttpClient()

  override def execute(request: String) = {
    val req = new RequestBuilder()
      .setReadTimeout(readTimeoutMs)
      .setRequestTimeout(requestTimeoutMs)
      .setUrl(rpcUrl)
      .setBody(request)
      .addHeader("Content-Type", "application/json")
      .setMethod("POST")
    Mono.fromFuture(client.executeRequest(req).toCompletableFuture
      .thenApply(_.getResponseBody))
  }
}
