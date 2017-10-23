package scalether.abi.array

import java.math.BigInteger

import scalether.abi.{Type, Uint256Type}

class VarArrayType[T](`type`: Type[T]) extends ArrayType[T](`type`) {
  override def size = None

  def string = s"${`type`.string}[]"

  override def encode(list: List[T]) =
    Uint256Type.encode(BigInteger.valueOf(list.size)) ++ super.encode(list)

  def decode(bytes: Array[Byte], offset: Int) = {
    val length = Uint256Type.decode(bytes, offset)
    decode(length.value.intValue(), bytes, length.offset)
  }
}

object VarArrayType {
  def apply[T](`type`: Type[T]): VarArrayType[T] = new VarArrayType[T](`type`)
}