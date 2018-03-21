package scalether.core

import java.math.BigInteger
import java.util

import io.daonomic.cats.mono.implicits._
import io.daonomic.rpc.mono.MonoTransport
import reactor.core.publisher.Mono
import scalether.domain.request.{LogFilter, Transaction}
import scalether.domain.response.{Block, Log, TransactionReceipt}
import scalether.domain.{Address, response}

import scala.collection.JavaConverters._

class MonoEthereum(transport: MonoTransport)
  extends Ethereum[Mono](transport) {

  override def web3ClientVersion(): Mono[String] =
    super.web3ClientVersion()

  override def web3Sha3(data: String): Mono[String] =
    super.web3Sha3(data)

  override def netVersion(): Mono[String] =
    super.netVersion()

  override def netListening(): Mono[Boolean] =
    super.netListening()

  override def ethBlockNumber(): Mono[BigInteger] =
    super.ethBlockNumber()

  override def ethGetBlockByHash(hash: String): Mono[Block] =
    super.ethGetBlockByHash(hash)

  override def ethGetBlockByNumber(number: BigInteger): Mono[Block] =
    super.ethGetBlockByNumber(number)

  override def ethCall(transaction: Transaction, defaultBlockParameter: String): Mono[Array[Byte]] =
    super.ethCall(transaction, defaultBlockParameter)

  override def ethSendTransaction(transaction: Transaction): Mono[String] =
    super.ethSendTransaction(transaction)

  override def ethSendRawTransaction(transaction: String): Mono[String] =
    super.ethSendRawTransaction(transaction)

  override def ethGetTransactionReceipt(hash: String): Mono[Option[TransactionReceipt]] =
    super.ethGetTransactionReceipt(hash)

  override def ethGetTransactionByHash(hash: String): Mono[response.Transaction] =
    super.ethGetTransactionByHash(hash)

  override def ethGetTransactionCount(address: Address, defaultBlockParameter: String): Mono[BigInteger] =
    super.ethGetTransactionCount(address, defaultBlockParameter)

  override def netPeerCount(): Mono[BigInteger] =
    super.netPeerCount()

  override def ethGetBalance(address: Address, defaultBlockParameter: String): Mono[BigInteger] =
    super.ethGetBalance(address, defaultBlockParameter)

  override def ethGasPrice(): Mono[BigInteger] =
    super.ethGasPrice()

  override def ethGetLogs(filter: LogFilter): Mono[List[Log]] =
    super.ethGetLogs(filter)

  override def ethNewFilter(filter: LogFilter): Mono[BigInteger] =
    super.ethNewFilter(filter)

  override def ethGetFilterChanges(id: BigInteger): Mono[List[Log]] =
    super.ethGetFilterChanges(id)

  def ethGetLogsJava(filter: LogFilter): Mono[util.List[Log]] =
    super.ethGetLogs(filter).map(_.asJava)

  def ethGetFilterChangesJava(id: BigInteger): Mono[util.List[Log]] =
    super.ethGetFilterChanges(id).map(_.asJava)

  override def ethGetCode(address: Address, defaultBlockParameter: String): Mono[String] =
    super.ethGetCode(address, defaultBlockParameter)
}