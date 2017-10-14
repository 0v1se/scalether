package scalether.core

import java.math.BigInteger

import scalether.core.data.Address

case class TransactionReceipt(transactionHash: String,
                              transactionIndex: BigInteger,
                              blockHash: String,
                              blockNumber: BigInteger,
                              cumulativeGasUsed: BigInteger,
                              gasUsed: BigInteger,
                              contractAddress: Address,
                              from: String,
                              to: String,
                              logs: List[Log])
