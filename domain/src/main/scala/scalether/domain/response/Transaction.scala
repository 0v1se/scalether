package scalether.domain.response

import java.math.BigInteger

import scalether.domain.{Address, Word}

case class Transaction(hash: Word,
                       nonce: BigInteger,
                       blockHash: Word,
                       blockNumber: BigInteger,
                       transactionIndex: BigInteger,
                       from: Address,
                       to: Address,
                       value: BigInteger,
                       gasPrice: BigInteger,
                       gas: BigInteger,
                       input: String)
