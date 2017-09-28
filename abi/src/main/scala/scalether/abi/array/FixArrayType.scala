package scalether.abi.array

import scalether.abi.Type

class FixArrayType[T](length: Int, `type`: Type[T]) extends ArrayType[T](`type`) {
  assert(length != 0)
  assert(`type`.size.isDefined)

  override def size = Some(`type`.size.get * length)

  def string = s"${`type`.string}[$length]"

  def decode(bytes: Array[Byte], offset: Int) =
    decode(length, bytes, offset)
}
