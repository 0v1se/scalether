package io.daonomic.blockchain.common

import java.math.BigInteger

import cats.implicits._
import cats.Monad
import io.daonomic.blockchain.Notify
import io.daonomic.blockchain.state.State

import scala.language.higherKinds

abstract class AbstractListenService[F[_]](confidence: Int, state: State[BigInteger, F])
                                          (implicit m: Monad[F]) {
  def check(blockNumber: BigInteger): F[Unit] = for {
    saved <- state.get
    _ <- fetchAndNotify(blockNumber, saved)
    _ <- state.set(blockNumber)
  } yield ()

  private def fetchAndNotify(blockNumber: BigInteger, saved: Option[BigInteger]): F[Unit] = {
    val from = saved.getOrElse(blockNumber.subtract(BigInteger.ONE))
    val start = from.subtract(BigInteger.valueOf(confidence - 1))
    val numbers = blockNumbers(start, blockNumber)
    Notify.every(numbers)(fetchAndNotify(blockNumber))
  }

  private def blockNumbers(from: BigInteger, to: BigInteger): List[BigInteger] = {
    if (from.compareTo(to) >= 0)
      Nil
    else if (to.subtract(from).compareTo(BigInteger.TEN.pow(3)) >= 0)
      throw new IllegalArgumentException("unable to process more than 1000 blocks")
    else if (from.compareTo(BigInteger.ZERO) < 0)
      blockNumbers(from.add(BigInteger.ONE), to)
    else
      from.add(BigInteger.ONE) :: blockNumbers(from.add(BigInteger.ONE), to)
  }

  protected def fetchAndNotify(latestBlock: BigInteger)(block: BigInteger): F[Unit]
}
