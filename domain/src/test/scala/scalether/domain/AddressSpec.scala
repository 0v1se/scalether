package scalether.domain

import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen
import org.scalatest.FlatSpec
import org.scalatest.prop.PropertyChecks

class AddressSpec extends FlatSpec with PropertyChecks {
  val address = Gen.listOfN(20, arbitrary[Byte])
    .map(_.toArray)

  "Address" should "be equal if bytes are equals" in {
    forAll(address) { bytes =>
      assert(Address(bytes) == Address(bytes))
    }
  }
}
