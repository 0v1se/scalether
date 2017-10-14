package scalether.extra.transaction

import org.scalatest.FlatSpec
import org.scalatest.prop.PropertyChecks
import org.web3j.rlp.RlpString
import scalether.core.request.Transaction
import scalether.domain.implicits._
import scalether.test.Generators

class TransactionSignerSpec extends FlatSpec with PropertyChecks {
  "TransactionSigner" should "encode rlp for transaction" in {
    forAll(Generators.address) { address =>
      val list = TransactionSigner.asRlp(Transaction(
        nonce = Some(0),
        gasPrice = Some(1),
        gas = Some(10),
        to = Some(address),
        value = Long.MaxValue
      ))
      assert(list.length == 6)
      assert(list(3) == RlpString.create(address.bytes))
    }
  }
}
