package scalether.test

import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen

object Generators {
  val address = Gen.listOfN(20, arbitrary[Byte])
}
