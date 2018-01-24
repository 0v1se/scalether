package scalether.domain.response

import java.math.BigInteger

case class Block(number: BigInteger,
                 hash: String,
                 parentHash: String,
                 nonce: String,
                 sha3Uncles: String,
                 logsBloom: String,
                 transactionsRoot:String,
                 stateRoot: String,
                 miner: String,
                 difficulty: BigInteger,
                 totalDifficulty: BigInteger,
                 extraData: String,
                 size: BigInteger,
                 gasLimit: BigInteger,
                 gasUsed: BigInteger,
                 transactions: List[String],
                 timestamp: BigInteger)
