package scalether.extra.log

import java.math.BigInteger

import cats.implicits._
import cats.Monad
import scalether.core.Ethereum

import scala.language.higherKinds

/**
  * алгоритм такой
  * 1. если сохраненное раньше значение = текущему, то выходим без действий
  * 2. загружаем логи.
  *   а) fromBlock = от какого блока есть
  *   b) toBlock = value from p.1
  *
  */
class LogListenerService[F[_]: Monad](ethereum: Ethereum[F], state: LogListenerState[F], listener: LogListener[F]) {
  def check(): F[Unit] = {
    for {
      fromEthereum <- ethereum.ethBlockNumber()
      saved <- state.lastKnownBlockNumber.map(_.getOrElse(BigInteger.ZERO))
    } yield saved
  }
}
