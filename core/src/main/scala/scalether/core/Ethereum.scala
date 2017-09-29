package scalether.core

import cats.MonadError
import cats.implicits._

import scala.language.higherKinds

class Ethereum[F[_]](service: EthereumService[F])(implicit me: MonadError[F, Throwable]) {

  def web3ClientVersion(): F[String] =
    exec("web3_clientVersion")

  def web3Sha3(data: String): F[String] =
    exec("web3_sha3", data)

  def netVersion(): F[String] =
    exec("net_version")

  def netListening(): F[Boolean] =
    exec("net_listening")

  def ethCall(transaction: Transaction): F[String] =
    exec("eth_call", transaction)

  def ethSendTransactin(transaction: Transaction): F[String] =
    exec("eth_sendTransaction", transaction)

  def ethSendRawTransaction(transaction: String): F[String] =
    exec("eth_sendRawTransaction", transaction)

  def ethGetTransactionReceipt(hash: String): F[Option[TransactionReceipt]] =
    exec("eth_getTransactionReceipt", hash)

  def netPeerCount(): F[BigInt] =
    exec("net_peerCount")

  def ethGetBalance(address: String, defaultBlockParameter: String): F[BigInt] =
    exec("eth_getBalance", address, defaultBlockParameter)

  def ethGasPrice(): F[BigInt] =
    exec("eth_gasPrice")

  private def exec[T](method: String, params: Any*)(implicit mf: Manifest[T]): F[T] = {
    service.execute[T](Request(1, method, params: _*)).flatMap {
      response =>
        response.result match {
          case Some(r) => me.pure(r)
          case None => me.raiseError(new RpcException(response.error.getOrElse(Error.default)))
        }
    }
  }
}
