package scalether.extra.log

import java.math.BigInteger

import scala.language.higherKinds

trait LogListenerState[F[_]] {
  def lastKnownBlockNumber: F[Option[BigInteger]]

}
