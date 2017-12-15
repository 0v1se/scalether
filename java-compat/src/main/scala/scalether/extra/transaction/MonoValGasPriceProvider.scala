package scalether.extra.transaction

import java.math.BigInteger

import reactor.core.publisher.Mono
import scalether.java.implicits._

class MonoValGasPriceProvider(value: BigInteger) extends ValGasPriceProvider[Mono](value)
