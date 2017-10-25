package scalether.abi.array

import scalether.abi.{Decoded, Type}

import scala.reflect.ClassTag

abstract class ArrayType[T](`type`: Type[T])
                           (implicit classTag: ClassTag[T])
  extends Type[Array[T]] {

  def encode(value: Array[T]) =
    value.flatMap(t => `type`.encode(t))

  protected def decode(length: Int, bytes: Array[Byte], offset: Int): Decoded[Array[T]] = {
    var current = offset
    val list:IndexedSeq[T] = for (_ <- 1 to length) yield {
      val decoded = `type`.decode(bytes, current)
      current = decoded.offset
      decoded.value
    }
    Decoded(list.toArray, current)
  }
}
