package scalether.abi

import org.scalatest.FlatSpec
import scalether.util.Hex

class UintSpec extends FlatSpec {
  "Uint64" should "encode values" in {
    assert(Uint64Type.encode(BigInt(0)) sameElements Hex.hexToBytes("%064d".format(0)))
    assert(Uint64Type.encode(BigInt(Long.MaxValue)) sameElements Hex.hexToBytes(List.fill(48)('0').mkString + "7fffffffffffffff"))
  }
}
