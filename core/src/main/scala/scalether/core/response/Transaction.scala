package scalether.core.response

case class Transaction(hash: String,
                       nonce: BigInt,
                       blockHash: String,
                       blockNumber: BigInt,
                       transactionIndex: BigInt,
                       from: String,
                       to: Option[String],
                       value: BigInt,
                       gasPrice: BigInt,
                       gas: BigInt,
                       input: String)
