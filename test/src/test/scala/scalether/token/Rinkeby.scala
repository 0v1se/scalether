package scalether.token

import java.math.BigInteger

import cats.implicits._
import scalether.core.{Ethereum, EthereumService}
import scalether.domain.implicits._
import scalether.extra.timer.tries.implicits._
import scalether.extra.transaction.{SigningTransactionSender, SimpleNonceProvider, TransactionPoller}
import scalether.transport.ScalajHttpTransportService

import scala.util.Try

trait Rinkeby {
  val ethereum = new Ethereum[Try](new EthereumService[Try](new ScalajHttpTransportService("https://rinkeby.infura.io/dx4qgyVaUCtWOH74E8TY"), log = true))
  val sender = new SigningTransactionSender[Try](
    ethereum, new SimpleNonceProvider[Try](ethereum),
    "0xc66d094ed928f7840a6b0d373c1cd825c97e3c7c",
    "0x00120de4b1518cf1f16dc1b02f6b4a8ac29e870174cb1d8575f578480930250a",
    2000000,
    "100000000000"
  )
  val poller = new TransactionPoller[Try](ethereum)
  val initial: BigInteger = "50000000000000000000"
}
