package scalether.abi

import org.scalatest.FlatSpec
import scalether.abi.tuple.Tuple2Type

class EventSpec extends FlatSpec {
  "Event" should "calculate id" in {
    assert(Event("Event", Tuple2Type(StringType, StringType)).id == "0x39b8d23135cdeca3f85b347e5285f40c9b1de764cf9f8126e7f3b34d77ff0cf0")
  }
}
