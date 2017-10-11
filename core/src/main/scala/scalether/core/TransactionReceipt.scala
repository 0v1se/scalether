package scalether.core

import java.math.BigInteger

case class TransactionReceipt(transactionHash: String,
                              transactionIndex: BigInteger,
                              blockHash: String,
                              blockNumber: BigInteger,
                              cumulativeGasUsed: BigInteger,
                              gasUsed: BigInteger,
                              contractAddress: String,
                              from: String,
                              to: String,
                              logs: List[Log])
