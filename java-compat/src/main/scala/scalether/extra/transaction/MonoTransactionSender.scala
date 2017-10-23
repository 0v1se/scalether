package scalether.extra.transaction

import reactor.core.publisher.Mono

trait MonoTransactionSender extends TransactionSender[Mono] {

}
