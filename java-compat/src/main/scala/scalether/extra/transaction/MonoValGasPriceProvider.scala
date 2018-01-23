package scalether.extra.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.java.implicits._
import scalether.transaction.ValGasPriceProvider

class MonoValGasPriceProvider(value: BigInteger) extends ValGasPriceProvider[Mono](value) with MonoGasPriceProvider
