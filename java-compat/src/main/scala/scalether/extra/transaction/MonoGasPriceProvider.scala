package scalether.extra.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono

trait MonoGasPriceProvider extends GasPriceProvider[Mono] {
  override def gasPrice: Mono[BigInteger]
}
