package scalether.domain

case class Response[T](id: Long,
                       result: Option[T],
                       error: Option[Error])
