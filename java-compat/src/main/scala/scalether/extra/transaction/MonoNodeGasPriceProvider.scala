package scalether.extra.transaction

import reactor.core.publisher.Mono
import scalether.core.MonoEthereum
import scalether.transaction.NodeGasPriceProvider

class MonoNodeGasPriceProvider(monoEthereum: MonoEthereum) extends NodeGasPriceProvider[Mono](monoEthereum) with MonoGasPriceProvider
