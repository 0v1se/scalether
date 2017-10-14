package scalether.core

import java.util.concurrent.CompletableFuture

import scalether.extra.transaction.TransactionSender

trait JavaTransactionSender extends TransactionSender[CompletableFuture] {

}
