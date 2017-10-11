package scalether.core

import java.math.BigInteger
import java.util.concurrent.CompletableFuture

import scalether.java.implicits
import scalether.core.request.LogFilter

class JavaEthereum(service: JavaEthereumService) extends Ethereum[CompletableFuture](service)(implicits.completableFutureInstance) {
  override def web3ClientVersion(): CompletableFuture[String] = super.web3ClientVersion()

  override def web3Sha3(data: String): CompletableFuture[String] = super.web3Sha3(data)

  override def netVersion(): CompletableFuture[String] = super.netVersion()

  override def netListening(): CompletableFuture[Boolean] = super.netListening()

  override def ethCall(transaction: request.Transaction): CompletableFuture[String] = super.ethCall(transaction)

  override def ethSendTransaction(transaction: request.Transaction): CompletableFuture[String] = super.ethSendTransaction(transaction)

  override def ethSendRawTransaction(transaction: String): CompletableFuture[String] = super.ethSendRawTransaction(transaction)

  override def ethGetTransactionReceipt(hash: String): CompletableFuture[TransactionReceipt] = super.ethGetTransactionReceipt(hash)

  override def ethGetTransactionByHash(hash: String): CompletableFuture[response.Transaction] = super.ethGetTransactionByHash(hash)

  override def netPeerCount(): CompletableFuture[BigInteger] = super.netPeerCount()

  override def ethGetBalance(address: String, defaultBlockParameter: String): CompletableFuture[BigInteger] = super.ethGetBalance(address, defaultBlockParameter)

  override def ethGasPrice(): CompletableFuture[BigInteger] = super.ethGasPrice()

  override def ethGetLogs(filter: LogFilter): CompletableFuture[List[Log]] = super.ethGetLogs(filter)

  override def ethNewFilter(filter: LogFilter): CompletableFuture[BigInteger] = super.ethNewFilter(filter)

  override def ethGetFilterChanges(id: BigInteger): CompletableFuture[List[Log]] = super.ethGetFilterChanges(id)
}
