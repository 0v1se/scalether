package scalether.domain.request

import scalether.domain.{Address, Word}

case class LogFilter(topics: List[Word] = Nil,
                     address: List[Address] = Nil,
                     fromBlock: String = "latest",
                     toBlock: String = "latest")
