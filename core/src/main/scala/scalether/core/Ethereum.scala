package scalether.core

import java.math.BigInteger

import cats.MonadError
import cats.implicits._
import scalether.core.data.Address

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

  def ethBlockNumber(): F[BigInteger] =
    exec("eth_blockNumber")

  def ethCall(transaction: request.Transaction): F[String] =
    exec("eth_call", transaction)

  def ethSendTransaction(transaction: request.Transaction): F[String] =
    exec("eth_sendTransaction", transaction)

  def ethSendRawTransaction(transaction: String): F[String] =
    exec("eth_sendRawTransaction", transaction)

  def ethGetTransactionReceipt(hash: String): F[TransactionReceipt] =
    exec("eth_getTransactionReceipt", hash)

  def ethGetTransactionByHash(hash: String): F[response.Transaction] =
    exec("eth_getTransactionByHash", hash)

  def netPeerCount(): F[BigInteger] =
    exec("net_peerCount")

  def ethGetBalance(address: Address, defaultBlockParameter: String): F[BigInteger] =
    exec("eth_getBalance", address, defaultBlockParameter)

  def ethGasPrice(): F[BigInteger] =
    exec("eth_gasPrice")

  def ethGetLogs(filter: request.LogFilter): F[List[Log]] =
    exec("eth_getLogs", filter)

  def ethNewFilter(filter: request.LogFilter): F[BigInteger] =
    exec("eth_newFilter", filter)

  def ethGetFilterChanges(id: BigInteger): F[List[Log]] =
    exec("eth_getFilterChanges", id)

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
