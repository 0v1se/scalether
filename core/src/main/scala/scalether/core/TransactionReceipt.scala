package scalether.core

case class TransactionReceipt(transactionHash: String,
                              transactionIndex: String,
                              blockHash: String,
                              blockNumber: String,
                              cumulativeGasUsed: String,
                              gasUsed: String,
                              contractAddress: String,
                              from: String,
                              to: String)
