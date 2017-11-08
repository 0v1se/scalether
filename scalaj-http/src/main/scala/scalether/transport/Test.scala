package scalether.transport

import java.math.BigInteger

import cats.implicits._
import scalether.core.{Ethereum, EthereumService}
import scalether.domain.request.LogFilter
import scalether.domain.response.Log
import scalether.util.Hex

import scala.util.Try

object Test extends App {
  val service = new EthereumService[Try](new ScalajHttpTransportService("http://localhost:8545"), true)
  val eth = new Ethereum[Try](service)

  private val logs: List[Log] = eth.ethGetLogs(LogFilter(topics = List(), fromBlock = Hex.prefixed(BigInteger.valueOf(4642716)), toBlock = "pending")).get
  println(service.json.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(logs))
  println(logs.size)
}
