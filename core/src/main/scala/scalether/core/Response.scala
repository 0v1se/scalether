package scalether.core

case class Response[T](id: Long,
                       result: Option[T],
                       error: Option[Error])
