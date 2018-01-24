package scalether.domain.response

import java.math.BigInteger

import scalether.domain.Address

case class TransactionReceipt(transactionHash: String,
                              transactionIndex: BigInteger,
                              blockHash: String,
                              blockNumber: BigInteger,
                              cumulativeGasUsed: BigInteger,
                              gasUsed: BigInteger,
                              contractAddress: Address,
                              status: BigInteger,
                              from: String,
                              to: String,
                              logs: List[Log]) {
  def success: Boolean = status == BigInteger.ONE
}
