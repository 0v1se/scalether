package scalether.abi

import org.scalatest.FlatSpec
import scalether.abi.AbiTestConst._
import scalether.abi.array.FixArrayType

class FixArraySpec extends FlatSpec {
  val arr1 = new FixArrayType(1, Uint256Type)
  val arr2 = new FixArrayType(2, Uint256Type)

  "FixArrayType" should "decode 1-item array" in {
    val result = arr1.decode(one, 0)
    assert(result.offset == 32)
    assert(result.value == List(BigInt(1)))
  }

  it should "decode arrays with greater lengths" in {
    val result = arr2.decode(ten ++ one, 0)
    assert(result.offset == 64)
    assert(result.value == List(BigInt(10), BigInt(1)))
  }

  it should "encode arrays" in {
    val result = arr2.encode(List(BigInt(Long.MaxValue), BigInt(0)))
    assert(result sameElements (maxLong ++ zero))
  }
}
