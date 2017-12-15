package scalether.test

import cats.implicits._
import scalether.core.{Ethereum, EthereumService}
import scalether.domain.implicits._
import scalether.extra.timer.tries.implicits._
import scalether.extra.transaction.{SigningTransactionSender, SimpleNonceProvider, TransactionPoller, ValGasPriceProvider}
import scalether.transport.ScalajHttpTransportService

import scala.util.Try

trait IntegrationSpec {
  val ethereum = new Ethereum[Try](new EthereumService[Try](new ScalajHttpTransportService("http://localhost:8545"), log = true))
  val sender = new SigningTransactionSender[Try](
    ethereum,
    new SimpleNonceProvider[Try](ethereum),
    org.web3j.utils.Numeric.toBigInt("0x00120de4b1518cf1f16dc1b02f6b4a8ac29e870174cb1d8575f578480930250a"),
    2000000,
    new ValGasPriceProvider[Try](10)
  )
  val poller = new TransactionPoller[Try](ethereum)
}
