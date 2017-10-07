package scalether.core

case class Log(logIndex: BigInt,
               transactionIndex: BigInt,
               transactionHash: String,
               blockHash: String,
               blockNumber: BigInt,
               address: String,
               data: String,
               topics: List[String],
               `type`: String)
