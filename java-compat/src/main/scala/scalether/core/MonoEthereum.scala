package scalether.core

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.domain.Address
import scalether.domain.request.{LogFilter, Transaction}
import scalether.java.implicits._

class MonoEthereum(service: MonoEthereumService)
  extends Ethereum[Mono](service) {

  override def web3ClientVersion() =
    super.web3ClientVersion()

  override def web3Sha3(data: String) =
    super.web3Sha3(data)

  override def netVersion() =
    super.netVersion()

  override def netListening() =
    super.netListening()

  override def ethBlockNumber() =
    super.ethBlockNumber()

  override def ethCall(transaction: Transaction, defaultBlockParameter: String) =
    super.ethCall(transaction, defaultBlockParameter)

  override def ethSendTransaction(transaction: Transaction) =
    super.ethSendTransaction(transaction)

  override def ethSendRawTransaction(transaction: String) =
    super.ethSendRawTransaction(transaction)

  override def ethGetTransactionReceipt(hash: String) =
    super.ethGetTransactionReceipt(hash)

  override def ethGetTransactionByHash(hash: String) =
    super.ethGetTransactionByHash(hash)

  override def ethGetTransactionCount(address: Address, defaultBlockParameter: String) =
    super.ethGetTransactionCount(address, defaultBlockParameter)

  override def netPeerCount() =
    super.netPeerCount()

  override def ethGetBalance(address: Address, defaultBlockParameter: String) =
    super.ethGetBalance(address, defaultBlockParameter)

  override def ethGasPrice() =
    super.ethGasPrice()

  override def ethGetLogs(filter: LogFilter) =
    super.ethGetLogs(filter)

  override def ethNewFilter(filter: LogFilter) =
    super.ethNewFilter(filter)

  override def ethGetFilterChanges(id: BigInteger) =
    super.ethGetFilterChanges(id)

  override def ethGetCode(address: Address, defaultBlockParameter: String) =
    super.ethGetCode(address, defaultBlockParameter)
}
