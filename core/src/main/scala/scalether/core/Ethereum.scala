package scalether.core

import java.math.BigInteger

import cats.MonadError
import io.daonomic.rpc.RpcHttpClient
import io.daonomic.rpc.transport.RpcTransport
import scalether.domain.request.{LogFilter, Transaction}
import scalether.domain.response.{Block, Log, TransactionReceipt}
import scalether.domain.{Address, response}

import scala.language.higherKinds

class Ethereum[F[_]](transport: RpcTransport[F])
                    (implicit me: MonadError[F, Throwable])
  extends EthereumRpcClient[F](transport) {

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

  def ethGetBlockByHash(hash: String): F[Block] =
    exec("eth_getBlockByHash", hash, false)

  def ethGetBlockByNumber(number: BigInteger): F[Block] =
    exec("eth_getBlockByNumber", number, false)

  def ethCall(transaction: Transaction, defaultBlockParameter: String): F[Array[Byte]] =
    exec("eth_call", transaction, defaultBlockParameter)

  def ethEstimateGas(transaction: Transaction, defaultBlockParameter: String): F[BigInteger] =
    exec("eth_estimateGas", transaction, defaultBlockParameter)

  def ethSendTransaction(transaction: Transaction): F[String] =
    exec("eth_sendTransaction", transaction)

  def ethSendRawTransaction(transaction: String): F[String] =
    exec("eth_sendRawTransaction", transaction)

  def ethGetTransactionReceipt(hash: String): F[Option[TransactionReceipt]] =
    execOption("eth_getTransactionReceipt", hash)

  def ethGetTransactionByHash(hash: String): F[Option[response.Transaction]] =
    execOption("eth_getTransactionByHash", hash)

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
}
