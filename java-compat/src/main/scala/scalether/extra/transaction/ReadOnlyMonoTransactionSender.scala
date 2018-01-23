package scalether.extra.transaction

import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.domain.Address
import scalether.java.implicits._
import scalether.transaction.ReadOnlyTransactionSender

import scala.language.higherKinds

class ReadOnlyMonoTransactionSender(ethereum: MonoEthereum, from: Address)
  extends ReadOnlyTransactionSender[Mono](ethereum, from) with MonoTransactionSender {

}
