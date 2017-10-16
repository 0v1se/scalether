package scalether.domain

case class Request(id: Long,
                   method: String,
                   params: List[Any])

object Request {
  def apply(id: Long, method: String, params: Any*): Request =
    new Request(id, method, params.toList)
}
