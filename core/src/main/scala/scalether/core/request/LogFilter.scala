package scalether.core.request

case class LogFilter(topics: List[AnyRef] = Nil,
                     address: Option[String] = None,
                     fromBlock: String = "latest",
                     toBlock: String = "latest")
