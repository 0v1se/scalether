package scalether.abi

import java.math.BigInteger

import cats.implicits._
import org.scalatest.FlatSpec
import org.scalatest.mockito.MockitoSugar
import scalether.abi.array.VarArrayType
import scalether.abi.tuple.{Tuple1Type, Tuple4Type, UnitType}
import org.mockito.Mockito._

import scala.util.Try

class CallSpec extends FlatSpec with MockitoSugar {
  val totalSupply = Signature("totalSupply", UnitType, Tuple1Type(Uint256Type))

  "Call" should "encode totalSupply() returns (uint256)" in {
    assert(Call.encode(totalSupply, ()) == "0x18160ddd")
  }

  it should "encode example from ethereum docs" in {
    val signature = Signature("f", new Tuple4Type(Uint256Type, new VarArrayType(Uint32Type), Bytes10Type, BytesType), UnitType)
    val result = Call.encode(signature, (BigInteger.valueOf(0x123), Array(BigInteger.valueOf(0x456), BigInteger.valueOf(0x789)), "1234567890".getBytes(), "Hello, world!".getBytes()))
    val testResult = "0x8be65246" + "0000000000000000000000000000000000000000000000000000000000000123" + "0000000000000000000000000000000000000000000000000000000000000080" + "3132333435363738393000000000000000000000000000000000000000000000" + "00000000000000000000000000000000000000000000000000000000000000e0" + "0000000000000000000000000000000000000000000000000000000000000002" + "0000000000000000000000000000000000000000000000000000000000000456" + "0000000000000000000000000000000000000000000000000000000000000789" + "000000000000000000000000000000000000000000000000000000000000000d" + "48656c6c6f2c20776f726c642100000000000000000000000000000000000000"
    assert(result == testResult)
  }

  it should "encode and return value" in {
    val exec = mock[String => Try[String]]
    when(exec.apply("0x18160ddd")).thenReturn(Try("000000000000000000000000000000000000000000000000000110D9316EC000"))
    val result = Call.call(totalSupply, ())(exec)
    assert(result.get == new BigInteger("300000000000000"))
  }
}
