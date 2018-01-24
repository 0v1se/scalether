package scalether.listener.block

import java.math.BigInteger

import cats.implicits._
import cats.Monad
import scalether.core.Ethereum
import scalether.listener.common.State

import scala.language.higherKinds

class BlockListenService[F[_]](ethereum: Ethereum[F], listener: BlockListener[F], state: State[BigInteger, F])
                              (implicit m: Monad[F]) {

  def check(): F[BigInteger] =
    if (listener.enabled) {
      checkInternal()
    } else {
      ethereum.ethBlockNumber()
    }

  private def checkInternal(): F[BigInteger] = for {
    blockNumber <- ethereum.ethBlockNumber()
    savedValue <- state.get
    changed <- notifyListener(blockNumber, savedValue)
    _ <- setState(blockNumber, changed)
  } yield blockNumber

  private def notifyListener(blockNumber: BigInteger, savedValue: Option[BigInteger]): F[Boolean] = savedValue match {
    case Some(value) if value == blockNumber => m.pure(false)
    case _ => listener.onBlock(blockNumber).map(_ => true)
  }

  private def setState(blockNumber: BigInteger, changed: Boolean): F[Unit] =
    if (changed)
      state.set(blockNumber)
    else
      m.unit
}
