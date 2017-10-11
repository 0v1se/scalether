package scalether.core.response

import java.math.BigInteger

case class Transaction(hash: String,
                       nonce: BigInteger,
                       blockHash: String,
                       blockNumber: BigInteger,
                       transactionIndex: BigInteger,
                       from: String,
                       to: Option[String],
                       value: BigInteger,
                       gasPrice: BigInteger,
                       gas: BigInteger,
                       input: String)
