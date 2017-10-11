package scalether.util

import scala.collection.JavaConverters._

object Lists {
  def toScala[T <: AnyRef](col: java.util.Collection[T]): List[T] = col.asScala.toList
  def toJava[T <: AnyRef](scala: List[T]): java.util.List[T] = scala.asJava
}
