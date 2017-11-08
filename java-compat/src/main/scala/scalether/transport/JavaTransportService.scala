package scalether.transport

import java.util.concurrent.CompletableFuture

import org.asynchttpclient.{DefaultAsyncHttpClient, RequestBuilder}
import scalether.core.TransportService

class JavaTransportService(rpcUrl: String, requestTimeoutMs: Int = 10000, readTimeoutMs: Int = 10000) extends TransportService[CompletableFuture] {
  private val client = new DefaultAsyncHttpClient()

  override def execute(request: String) = {
    val req = new RequestBuilder()
      .setReadTimeout(readTimeoutMs)
      .setRequestTimeout(requestTimeoutMs)
      .setUrl(rpcUrl)
      .setBody(request)
      .addHeader("Content-Type", "application/json")
      .setMethod("POST")
    client.executeRequest(req).toCompletableFuture
      .thenApply(_.getResponseBody)
  }
}
