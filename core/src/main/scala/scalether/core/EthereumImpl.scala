package scalether.core

import cats.MonadError
import cats.implicits._

import scala.language.higherKinds

class EthereumImpl[F[_]](service: EthereumService[F])(implicit me: MonadError[F, Throwable]) extends Ethereum[F] {

  def web3ClientVersion() =
    exec("web3_clientVersion")

  def web3Sha3(data: String) =
    exec("web3_sha3", data)

  def netVersion() =
    exec("net_version")

  def netListening() =
    exec("net_listening")

  def ethCall(transaction: Transaction) =
    exec("eth_call", transaction)

  def ethSendTransactin(transaction: Transaction) =
    exec("eth_sendTransaction", transaction)

  def ethSendRawTransaction(transaction: String) =
    exec("eth_sendRawTransaction", transaction)

  def ethGetTransactionReceipt(hash: String) =
    exec("eth_getTransactionReceipt", hash)

  override def netPeerCount() =
    exec("net_peerCount")

  def ethGetBalance(address: String, defaultBlockParameter: String) =
    exec("eth_getBalance", address, defaultBlockParameter)

  def ethGasPrice() =
    exec("eth_gasPrice")

  private def exec[T](method: String, params: Any*)(implicit mf: Manifest[T]): F[T] = {
    service.execute[T](Request(1, method, params:_*)).flatMap {
      response => response.result match {
        case Some(r) => me.pure(r)
        case None => me.raiseError(new RpcException(response.error.getOrElse(Error.default)))
      }
    }
  }
}
