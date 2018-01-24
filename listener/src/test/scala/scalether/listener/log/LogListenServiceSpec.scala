package scalether.listener.log

import java.math.BigInteger

import cats.implicits._
import org.mockito.Mockito._
import org.scalatest.FlatSpec
import org.scalatest.mockito.MockitoSugar
import scalether.core.Ethereum
import scalether.core.state.SimpleState

import scala.util.{Success, Try}

class LogListenServiceSpec extends FlatSpec with MockitoSugar {
  "LogListenerService" should "not do anything if no new block added" in {
    val state = new SimpleState[BigInteger, Try](Some(BigInteger.TEN))
    val ethereum = mock[Ethereum[Try]]
    val listener = mock[LogListener[Try]]

    when(ethereum.ethBlockNumber()).thenReturn(Success(BigInteger.TEN))

    val testing = new LogListenService(ethereum, 5, listener, state)
    testing.check().get

    verify(ethereum).ethBlockNumber()
    verifyNoMoreInteractions(ethereum, listener)
  }

/*
  it should "load logs from ZERO block" in {
    val state = new SimpleState[BigInteger, Try](None)
    val ethereum = mock[Ethereum[Try]]
    val listener = mock[LogListener[Try]]
    val filter = mock[LogFilter]
    val log = mock[Log]

    when(log.blockNumber).thenReturn(BigInteger.valueOf(9))
    when(ethereum.ethBlockNumber()).thenReturn(Success(BigInteger.TEN))
    when(listener.createFilter(Hex.prefixed(BigInteger.ZERO), Hex.prefixed(BigInteger.TEN))).thenReturn(Success(filter))
    when(ethereum.ethGetLogs(filter)).thenReturn(Success(List(log)))
    when(listener.onLog(log, confirmed = true)).thenReturn(Success())

    val testing = new LogListenerService(ethereum, 5, listener, state)
    testing.check().get

    verify(ethereum).ethBlockNumber()
    verify(ethereum).ethGetLogs(filter)
    verify(listener).createFilter(Hex.prefixed(BigInteger.ZERO), Hex.prefixed(BigInteger.TEN))
    verify(listener).onLog(log, confirmed = true)
    verifyNoMoreInteractions(ethereum, listener)
  }
*/
}
