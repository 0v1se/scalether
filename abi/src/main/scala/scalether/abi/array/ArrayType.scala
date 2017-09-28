package scalether.abi.array

import scalether.abi.{Decoded, Type}

abstract class ArrayType[T](`type`: Type[T]) extends Type[List[T]] {

  def encode(list: List[T]) =
    list.flatMap(t => `type`.encode(t)).toArray

  protected def decode(length: Int, bytes: Array[Byte], offset: Int): Decoded[List[T]] = {
    var current = offset
    val list:IndexedSeq[T] = for (_ <- 1 to length) yield {
      val decoded = `type`.decode(bytes, current)
      current = decoded.offset
      decoded.value
    }
    Decoded(list.toList, current)
  }
}
