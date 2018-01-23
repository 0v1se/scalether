package scalether.core

import cats.MonadError
import scalether.domain.response.parity.Trace

import scala.language.higherKinds

class Parity[F[_]](service: EthereumService[F])
                  (implicit me: MonadError[F, Throwable])
  extends RpcHttpClient[F](service) {

  def traceTransaction(txHash: String): F[List[Trace]] = {
    exec("trace_transaction", txHash)
  }
}
