package scalether.test

import cats.implicits._
import org.scalatest.FlatSpec
import scalether.core.{EthereumService, Parity}
import scalether.transport.ScalajHttpTransportService

import scala.util.Try

class ParitySpec extends FlatSpec {
  val parity = new Parity[Try](new EthereumService[Try](new ScalajHttpTransportService("http://ether-dev:8545"), log = true))

  "Parity" should "get transaction trace" taggedAs ManualTag in {
    println(parity.traceTransaction("0x1825ab1f99ed41fa62f5ff324cf29eee802e6c07d3a5e21d905ff1f1add54b86").get)
  }
}
