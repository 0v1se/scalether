package scalether.listener.block

import java.math.BigInteger

import cats.implicits._
import org.mockito.Matchers
import org.mockito.Mockito._
import org.scalatest.FlatSpec
import org.scalatest.mockito.MockitoSugar
import scalether.core.Ethereum
import scalether.listener.common.{State, VarState}

import scala.util.{Success, Try}

class BlockListenServiceSpec extends FlatSpec with MockitoSugar {
  "BlockListenService" should "notify if run first time" in {
    val (state, ethereum, listener) = prepare(None, BigInteger.TEN)
    when(listener.onBlock(BigInteger.TEN)).thenReturn(Success())

    val testing = new BlockListenService[Try](ethereum, listener, state)
    testing.check().get

    verify(state).set(BigInteger.TEN)
    verify(listener).onBlock(BigInteger.TEN)
    verifyAfter(ethereum, listener)
  }

  it should "notify if block is changed" in {
    val (state, ethereum, listener) = prepare(Some(BigInteger.ONE), BigInteger.TEN)
    when(listener.onBlock(BigInteger.TEN)).thenReturn(Success())

    val testing = new BlockListenService[Try](ethereum, listener, state)
    testing.check().get

    verify(state).set(BigInteger.TEN)
    verify(listener).onBlock(BigInteger.TEN)
    verifyAfter(ethereum, listener)
  }

  it should "not notify if block is the same" in {
    val (state, ethereum, listener) = prepare(Some(BigInteger.TEN), BigInteger.TEN)

    val testing = new BlockListenService[Try](ethereum, listener, state)
    testing.check().get

    verifyAfter(ethereum, listener)
  }

  private def prepare(stateValue: Option[BigInteger], blockNumber: BigInteger) = {
    val ethereum = mock[Ethereum[Try]]
    when(ethereum.ethBlockNumber()).thenReturn(Success(blockNumber))
    val listener = mock[BlockListener[Try]]
    when(listener.enabled).thenReturn(true)
    val state = mock[State[BigInteger, Try]]
    when(state.set(Matchers.any[BigInteger]())).thenReturn(Success())
    when(state.get).thenReturn(Success(stateValue))
    (state, ethereum, listener)
  }

  private def verifyAfter(ethereum: Ethereum[Try], listener: BlockListener[Try]): Unit = {
    verify(ethereum).ethBlockNumber()
    verify(listener).enabled
    verifyNoMoreInteractions(ethereum, listener)
  }
}
