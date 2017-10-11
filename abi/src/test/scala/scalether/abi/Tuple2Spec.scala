package scalether.abi

import java.math.BigInteger

import org.scalacheck.Gen
import org.scalatest.FlatSpec
import org.scalatest.prop.PropertyChecks
import scalether.abi.array.{FixArrayType, VarArrayType}
import scalether.abi.tuple.Tuple2Type

class Tuple2Spec extends FlatSpec with PropertyChecks {
  val tuple2Type = Tuple2Type(new FixArrayType(1, Uint256Type), new VarArrayType(Uint256Type))

  val pos = Gen.posNum[Int].map(i => BigInteger.valueOf(i))
  val list = Gen.listOf(pos)

  "Tuple2Type" should "encode var and fix arrays" in {
    forAll(for (i1 <- pos; i2 <- pos) yield (i1, i2)) { case (fixVal: BigInteger, varVal: BigInteger) =>
      val fixArray = List(fixVal)
      val varArray = List(varVal)

      val encoded = tuple2Type.encode((fixArray, varArray))
      val test = Uint256Type.encode(fixVal) ++
        Uint256Type.encode(BigInteger.valueOf(64)) ++
        Uint256Type.encode(BigInteger.valueOf(1)) ++
        Uint256Type.encode(varVal)
      assert(encoded sameElements test)
    }
  }

  it should "encode decoded" in {
    forAll(for (i <- pos; l1 <- list; l2 <- list) yield (i, l1, l2)) { case (start1: BigInteger, list1: List[BigInteger], list2: List[BigInteger]) =>
      val fixArray = start1 :: list1
      val varArray = list2
      val typ = Tuple2Type(new FixArrayType(fixArray.size, Uint256Type), new VarArrayType(Uint256Type))

      val encoded = typ.encode((fixArray, varArray))
      val decoded = typ.decode(encoded, 0)
      assert(decoded.offset == encoded.length)
      assert(decoded.value == (fixArray, varArray))
    }
  }
}
