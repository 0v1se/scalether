package scalether.extra.transaction

import java.util.concurrent.CompletableFuture

trait JavaTransactionSender extends TransactionSender[CompletableFuture] {

}
