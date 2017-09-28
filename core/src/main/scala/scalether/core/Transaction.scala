package scalether.core

case class Transaction(to: String,
                       from: Option[String] = None,
                       gas: Option[BigInt] = None,
                       gasPrice: Option[BigInt] = None,
                       value: BigInt = BigInt(0),
                       data: Option[String] = None)
