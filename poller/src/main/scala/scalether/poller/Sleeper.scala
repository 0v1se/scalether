package scalether.poller

import scala.language.higherKinds

trait Sleeper[F[_]] {
  def sleep(sleep: Long): F[Unit]
}