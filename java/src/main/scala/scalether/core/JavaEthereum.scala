package scalether.core
import java.math.BigInteger
import java.util.concurrent.CompletableFuture

import scalether.core.request.{LogFilter, Transaction}
import scalether.domain.Address
import scalether.java.implicits._

class JavaEthereum(service: JavaEthereumService) extends Ethereum[CompletableFuture](service) {
  override def web3ClientVersion() = super.web3ClientVersion()

  override def web3Sha3(data: String) = super.web3Sha3(data)

  override def netVersion() = super.netVersion()

  override def netListening() = super.netListening()

  override def ethBlockNumber() = super.ethBlockNumber()

  override def ethCall(transaction: Transaction) = super.ethCall(transaction)

  override def ethSendTransaction(transaction: Transaction) = super.ethSendTransaction(transaction)

  override def ethSendRawTransaction(transaction: String) = super.ethSendRawTransaction(transaction)

  override def ethGetTransactionReceipt(hash: String) = super.ethGetTransactionReceipt(hash)

  override def ethGetTransactionByHash(hash: String) = super.ethGetTransactionByHash(hash)

  override def netPeerCount() = super.netPeerCount()

  override def ethGetBalance(address: Address, defaultBlockParameter: String) = super.ethGetBalance(address, defaultBlockParameter)

  override def ethGasPrice() = super.ethGasPrice()

  override def ethGetLogs(filter: LogFilter) = super.ethGetLogs(filter)

  override def ethNewFilter(filter: LogFilter) = super.ethNewFilter(filter)

  override def ethGetFilterChanges(id: BigInteger) = super.ethGetFilterChanges(id)
}
