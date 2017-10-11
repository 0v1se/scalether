package scalether.core.request

import scalether.core.data.{Address, Hash}

case class LogFilter(topics: List[Hash] = Nil,
                     address: List[Address] = Nil,
                     fromBlock: String = "latest",
                     toBlock: String = "latest")
