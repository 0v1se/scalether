package scalether.extra.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.transaction.GasPriceProvider

trait MonoGasPriceProvider extends GasPriceProvider[Mono] {
  def gasPrice: Mono[BigInteger]
}
