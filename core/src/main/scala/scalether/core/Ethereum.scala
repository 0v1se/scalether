package scalether.core

import java.math.BigInteger

import cats.MonadError
import cats.implicits._
import scalether.domain.{Address, Error, Request, response}
import scalether.domain.request.{LogFilter, Transaction}
import scalether.domain.response.{Log, TransactionReceipt}

import scala.language.higherKinds

class Ethereum[F[_]](service: EthereumService[F])
                    (implicit me: MonadError[F, Throwable]) {

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

  def ethCall(transaction: Transaction, defaultBlockParameter: String): F[String] =
    exec("eth_call", transaction, defaultBlockParameter)

  def ethSendTransaction(transaction: Transaction): F[String] =
    exec("eth_sendTransaction", transaction)

  def ethSendRawTransaction(transaction: String): F[String] =
    exec("eth_sendRawTransaction", transaction)

  def ethGetTransactionReceipt(hash: String): F[Option[TransactionReceipt]] =
    execOption("eth_getTransactionReceipt", hash)

  def ethGetTransactionByHash(hash: String): F[response.Transaction] =
    exec("eth_getTransactionByHash", hash)

  def netPeerCount(): F[BigInteger] =
    exec("net_peerCount")

  def ethGetTransactionCount(address: Address, defaultBlockParameter: String): F[BigInteger] =
    exec("eth_getTransactionCount", address, defaultBlockParameter)

  def ethGetBalance(address: Address, defaultBlockParameter: String): F[BigInteger] =
    exec("eth_getBalance", address, defaultBlockParameter)

  def ethGasPrice(): F[BigInteger] =
    exec("eth_gasPrice")

  def ethGetLogs(filter: LogFilter): F[List[Log]] =
    exec("eth_getLogs", filter)

  def ethNewFilter(filter: LogFilter): F[BigInteger] =
    exec("eth_newFilter", filter)

  def ethGetFilterChanges(id: BigInteger): F[List[Log]] =
    exec("eth_getFilterChanges", id)

  def ethGetCode(address: Address, defaultBlockParameter: String): F[String] =
    exec("eth_getCode", address, defaultBlockParameter)

  private def exec[T](method: String, params: Any*)
                           (implicit mf: Manifest[T]): F[T] = {
    execOption[T](method, params:_*).flatMap {
      case Some(v) => me.pure(v)
      case None => me.raiseError(new RpcException(Error.default))
    }
  }

  private def execOption[T](method: String, params: Any*)
                     (implicit mf: Manifest[T]): F[Option[T]] = {
    service.execute[T](Request(1, method, params: _*)).flatMap {
      response =>
        response.error match {
          case Some(r) => me.raiseError(new RpcException(r))
          case None => me.pure(response.result)
        }
    }
  }
}
