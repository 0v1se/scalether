package scalether.extra.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.Address
import scalether.domain.request.Transaction

class MonoSimpleTransactionSender(ethereum: MonoEthereum, from: Address, gas: BigInteger, gasPrice: BigInteger)
  extends SimpleTransactionSender[Mono](ethereum, from, gas, gasPrice) with MonoTransactionSender {

  override def sendTransaction(transaction: Transaction): Mono[String] = super.sendTransaction(transaction)
}
