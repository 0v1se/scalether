package scalether.core

import scala.language.higherKinds

trait Ethereum[F[_]] {
  def web3ClientVersion(): F[String]

  def web3Sha3(data: String): F[String]

  def netVersion(): F[String]

  def netListening(): F[Boolean]

  def netPeerCount(): F[BigInt]

  def ethGasPrice(): F[BigInt]

  def ethCall(transaction: Transaction): F[String]

  def ethSendTransactin(transaction: Transaction): F[String]

  def ethSendRawTransaction(transaction: String): F[String]

  def ethGetTransactionReceipt(hash: String): F[Option[TransactionReceipt]]

  def ethGetBalance(address: String, defaultBlockParameter: String): F[BigInt]
}
