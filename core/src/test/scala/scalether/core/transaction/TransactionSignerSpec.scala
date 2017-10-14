package scalether.core.transaction

import org.scalatest.FlatSpec
import scalether.core.implicits._
import scalether.core.request.Transaction
import scalether.domain.Address

class TransactionSignerSpec extends FlatSpec {
  "TransactionSigner" should "encode rlp for transaction" in {
    val list = TransactionSigner.asRlp(Transaction(
      nonce = Some(0),
      gasPrice = Some(1),
      gas = Some(10),
      to = Some(Address("0xadd5355")),
      value = Long.MaxValue
    ))
  }
}
