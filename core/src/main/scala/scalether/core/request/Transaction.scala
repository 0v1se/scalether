package scalether.core.request

case class Transaction(to: Option[String] = None,
                       from: Option[String] = None,
                       gas: Option[BigInt] = None,
                       gasPrice: Option[BigInt] = None,
                       value: BigInt = BigInt(0),
                       data: Option[String] = None,
                       nonce: Option[Int] = None)
