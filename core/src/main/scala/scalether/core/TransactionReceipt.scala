package scalether.core

case class TransactionReceipt(transactionHash: String,
                              transactionIndex: BigInt,
                              blockHash: String,
                              blockNumber: BigInt,
                              cumulativeGasUsed: BigInt,
                              gasUsed: BigInt,
                              contractAddress: String,
                              from: String,
                              to: String,
                              logs: List[Log])
