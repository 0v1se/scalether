package io.daonomic.blockchain.transfer

import java.math.BigInteger

import cats.implicits._
import cats.MonadError
import io.daonomic.blockchain._
import io.daonomic.blockchain.common.AbstractListenService
import io.daonomic.blockchain.state.State

import scala.language.higherKinds

class TransferListenService[F[_]](blockchain: Blockchain[F], confidence: Int, listener: TransferListener[F], state: State[BigInteger, F])
                                 (implicit m: MonadError[F, Throwable])
  extends AbstractListenService[F](confidence, state) {

  def fetchAndNotify(latestBlock: BigInteger)(block: BigInteger): F[Unit] =
    blockchain.getTransactionsByBlock(block).flatMap(notifyListenerAboutTransfers(latestBlock, block))

  private def notifyListenerAboutTransfers(latestBlock: BigInteger, block: BigInteger)(transactions: List[Transaction]): F[Unit] = {
    Notify.every(transactions)(notifyTransaction(latestBlock))
  }

  private def notifyTransaction(latestBlock: BigInteger)(tx: Transaction): F[Unit] = {
    val confirmations = latestBlock.subtract(tx.blockNumber).intValue() + 1
    Notify.every(tx.outputs.zipWithIndex)(notifyTransactionOutput(tx, confirmations, TransferDirection.OUT))
  }

  private def notifyTransactionOutput(transaction: Transaction, confirmations: Int, direction: TransferDirection)(pair: (BalanceChange, Int)): F[Unit] = {
    val (change, idx) = pair
    listener.onTransfer(Transfer(transaction.id, idx, direction, change.address, change.value), confirmations, confirmations >= confidence)
  }
}