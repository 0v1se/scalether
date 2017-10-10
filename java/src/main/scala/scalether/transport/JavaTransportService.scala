package scalether.transport

import java.util.concurrent.CompletableFuture

import org.asynchttpclient.{DefaultAsyncHttpClient, RequestBuilder}
import scalether.core.TransportService

class JavaTransportService(rpcUrl: String) extends TransportService[CompletableFuture] {
  private val client = new DefaultAsyncHttpClient()

  override def execute(request: String) = {
    val req = new RequestBuilder()
      .setUrl(rpcUrl)
      .setBody(request)
      .setMethod("POST")
    client.executeRequest(req).toCompletableFuture
      .thenApply(_.getResponseBody)
  }
}
