package scalether.domain.request

import scalether.domain.{Address, Word}

import scala.language.implicitConversions

case class LogFilter(topics: List[TopicFilter] = Nil,
                     address: List[Address] = Nil,
                     fromBlock: String = "latest",
                     toBlock: String = "latest") {
  def blocks(fromBlock: String, toBlock: String): LogFilter =
    this.copy(fromBlock = fromBlock, toBlock = toBlock)
}

sealed trait TopicFilter {

}

object TopicFilter {
  implicit def simple(word: Word): SimpleTopicFilter = SimpleTopicFilter(word)
  def or(word: Word*): OrTopicFilter = OrTopicFilter(word.toList)
}

case class SimpleTopicFilter(word: Word) extends TopicFilter

case class OrTopicFilter(words: List[Word]) extends TopicFilter