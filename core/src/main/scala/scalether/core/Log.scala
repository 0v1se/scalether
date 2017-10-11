package scalether.core

import java.math.BigInteger

case class Log(logIndex: BigInteger,
               transactionIndex: BigInteger,
               transactionHash: String,
               blockHash: String,
               blockNumber: BigInteger,
               address: String,
               data: String,
               topics: List[String],
               `type`: String)
