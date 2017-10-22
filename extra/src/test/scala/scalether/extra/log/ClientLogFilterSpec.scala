package scalether.extra.log

import java.math.BigInteger

import cats.implicits._
import org.mockito.Mockito.when
import org.scalatest.FlatSpec
import org.scalatest.mockito.MockitoSugar
import scalether.core.Ethereum
import scalether.domain.implicits._
import scalether.domain.request.LogFilter

import scala.util.{Success, Try}

class ClientLogFilterSpec extends FlatSpec with MockitoSugar {
  "ClientLogFilter" should "save state" in {
    val ethereum = mock[Ethereum[Try]]
    val logFilter = LogFilter()
    val state = new TestState

    when(ethereum.ethGetLogs(logFilter.copy(fromBlock = "0x00", toBlock = "0x01"))).thenReturn(Success(List(mock, mock)))
    val filter = new ClientLogFilter[Try](ethereum, logFilter, state)
    val changes1 = filter.getChanges(1)
    assert(changes1.get.size == 2)
    assert(state.getBlock == Success(1: BigInteger))

    when(ethereum.ethGetLogs(logFilter.copy(fromBlock = "0x01", toBlock = "0x02"))).thenReturn(Success(List(mock, mock, mock)))
    val changes2 = filter.getChanges(2)
    assert(changes2.get.size == 3)
    assert(state.getBlock == Success(2: BigInteger))
  }

  class TestState extends LogFilterState[Try] {
    var state: BigInteger = BigInteger.ZERO

    def getBlock = Success(state)

    def setBlock(block: BigInteger) = {
      this.state = block
      Success()
    }
  }
}
