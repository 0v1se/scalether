package scalether.test

import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen
import scalether.domain.Address

object Generators {
  val address = Gen.listOfN(20, arbitrary[Byte])
    .map(_.toArray)
    .map(Address.apply)
}
