package io.daonomic.blockchain.transaction

import java.math.BigInteger

import cats.implicits._
import cats.Monad
import io.daonomic.blockchain.common.AbstractListenService
import io.daonomic.blockchain.state.State
import io.daonomic.blockchain.{Blockchain, Notify}

import scala.language.higherKinds

class TransactionListenService[F[_]](blockchain: Blockchain[F], confidence: Int, listener: TransactionListener[F], state: State[BigInteger, F])
                                    (implicit m: Monad[F])
  extends AbstractListenService[F](confidence, state) {

  override protected def fetchAndNotify(latestBlock: BigInteger)(block: BigInteger): F[Unit] =
    blockchain.getTransactionIdsByBlock(block).flatMap(notifyListenerAboutTransactions(latestBlock, block))

  private def notifyListenerAboutTransactions(latestBlock: BigInteger, block: BigInteger)(transactionIds: List[String]): F[Unit] = {
    Notify.every(transactionIds)(notifyListener(latestBlock, block))
  }

  private def notifyListener(latestBlock: BigInteger, block: BigInteger)(transactionHash: String): F[Unit] = {
    val confirmations = latestBlock.subtract(block).intValue() + 1
    listener.onTransaction(transactionHash, block, confirmations, confirmations >= confidence)
  }
}
