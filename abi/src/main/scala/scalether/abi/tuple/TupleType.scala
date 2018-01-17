package scalether.abi.tuple

import scalether.abi.Type

abstract class TupleType[T] extends Type[T] {
  override def size: Option[Int] = None

  def types: List[Type[_]]

  def headSize: Int = types.map(_.size.getOrElse(32)).sum

  def headOffset(idx: Int): Int =
    (0 until idx).map(i => types(i).size.getOrElse(32)).sum
}
