package scalether.util

import scala.collection.JavaConverters._

object Lists {
  def toScala[T <: AnyRef](javaList: java.util.Collection[T]): List[T] = javaList.asScala.toList
}
