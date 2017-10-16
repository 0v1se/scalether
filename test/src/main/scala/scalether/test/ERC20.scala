package scalether.test

import java.math.BigInteger

import cats.{Functor, Monad}
import cats.implicits._
import scalether.abi._
import scalether.abi.tuple._
import scalether.contract._
import scalether.core._
import scalether.domain._
import scalether.domain.request.Transaction
import scalether.domain.response.Log
import scalether.extra.transaction.{TransactionSender, _}
import scalether.util.Hex

import scala.language.higherKinds

class ERC20[F[_]](address: Address, sender: TransactionSender[F])(implicit f: Functor[F])
  extends Contract[F](address, sender) {

  def callApprove(spender: Address, value: BigInteger): F[Boolean] =
    call(Signature("approve", Tuple2Type(AddressType, Uint256Type), Tuple1Type(BoolType)), (spender, value))

  def approve(spender: Address, value: BigInteger): F[String] =
    sendTransaction(Signature("approve", Tuple2Type(AddressType, Uint256Type), Tuple1Type(BoolType)), (spender, value))

  def totalSupply: F[BigInteger] =
    call(Signature("totalSupply", UnitType, Tuple1Type(Uint256Type)), ())

  def callTransferFrom(from: Address, to: Address, value: BigInteger): F[Boolean] =
    call(Signature("transferFrom", Tuple3Type(AddressType, AddressType, Uint256Type), Tuple1Type(BoolType)), (from, to, value))

  def transferFrom(from: Address, to: Address, value: BigInteger): F[String] =
    sendTransaction(Signature("transferFrom", Tuple3Type(AddressType, AddressType, Uint256Type), Tuple1Type(BoolType)), (from, to, value))

  def balanceOf(who: Address): F[BigInteger] =
    call(Signature("balanceOf", Tuple1Type(AddressType), Tuple1Type(Uint256Type)), who)

  def callTransfer(to: Address, value: BigInteger): F[Boolean] =
    call(Signature("transfer", Tuple2Type(AddressType, Uint256Type), Tuple1Type(BoolType)), (to, value))

  def transfer(to: Address, value: BigInteger): F[String] =
    sendTransaction(Signature("transfer", Tuple2Type(AddressType, Uint256Type), Tuple1Type(BoolType)), (to, value))

  def allowance(owner: Address, spender: Address): F[BigInteger] =
    call(Signature("allowance", Tuple2Type(AddressType, AddressType), Tuple1Type(Uint256Type)), (owner, spender))

}

object ERC20 extends ContractObject {
  val name = "ERC20"
  val abi = "[{\"name\":\"approve\",\"type\":\"function\",\"inputs\":[{\"name\":\"spender\",\"type\":\"address\",\"components\":null},{\"name\":\"value\",\"type\":\"uint256\",\"components\":null}],\"outputs\":[{\"name\":\"\",\"type\":\"bool\",\"components\":null}],\"payable\":false,\"constant\":false},{\"name\":\"totalSupply\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"uint256\",\"components\":null}],\"payable\":false,\"constant\":true},{\"name\":\"transferFrom\",\"type\":\"function\",\"inputs\":[{\"name\":\"from\",\"type\":\"address\",\"components\":null},{\"name\":\"to\",\"type\":\"address\",\"components\":null},{\"name\":\"value\",\"type\":\"uint256\",\"components\":null}],\"outputs\":[{\"name\":\"\",\"type\":\"bool\",\"components\":null}],\"payable\":false,\"constant\":false},{\"name\":\"balanceOf\",\"type\":\"function\",\"inputs\":[{\"name\":\"who\",\"type\":\"address\",\"components\":null}],\"outputs\":[{\"name\":\"\",\"type\":\"uint256\",\"components\":null}],\"payable\":false,\"constant\":true},{\"name\":\"transfer\",\"type\":\"function\",\"inputs\":[{\"name\":\"to\",\"type\":\"address\",\"components\":null},{\"name\":\"value\",\"type\":\"uint256\",\"components\":null}],\"outputs\":[{\"name\":\"\",\"type\":\"bool\",\"components\":null}],\"payable\":false,\"constant\":false},{\"name\":\"allowance\",\"type\":\"function\",\"inputs\":[{\"name\":\"owner\",\"type\":\"address\",\"components\":null},{\"name\":\"spender\",\"type\":\"address\",\"components\":null}],\"outputs\":[{\"name\":\"\",\"type\":\"uint256\",\"components\":null}],\"payable\":false,\"constant\":true},{\"name\":\"Approval\",\"inputs\":[{\"name\":\"owner\",\"type\":\"address\",\"indexed\":true,\"components\":null},{\"name\":\"spender\",\"type\":\"address\",\"indexed\":true,\"components\":null},{\"name\":\"value\",\"type\":\"uint256\",\"indexed\":false,\"components\":null}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"Transfer\",\"inputs\":[{\"name\":\"from\",\"type\":\"address\",\"indexed\":true,\"components\":null},{\"name\":\"to\",\"type\":\"address\",\"indexed\":true,\"components\":null},{\"name\":\"value\",\"type\":\"uint256\",\"indexed\":false,\"components\":null}],\"anonymous\":false,\"type\":\"event\"}]"
  val bin = "0x"
}

case class Approval(owner: Address, spender: Address, value: BigInteger)

object Approval {
  val event = Event("Approval", List(AddressType, AddressType, Uint256Type), Tuple2Type(AddressType, AddressType), Tuple1Type(Uint256Type))

  def apply(log: Log): Approval = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val owner = event.indexed.type1.decode(Hex.toBytes(log.topics(1)), 0).value
    val spender = event.indexed.type2.decode(Hex.toBytes(log.topics(2)), 0).value
    val value = decodedData
    Approval(owner, spender, value)
  }
}

case class Transfer(from: Address, to: Address, value: BigInteger)

object Transfer {
  val event = Event("Transfer", List(AddressType, AddressType, Uint256Type), Tuple2Type(AddressType, AddressType), Tuple1Type(Uint256Type))

  def apply(log: Log): Transfer = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val from = event.indexed.type1.decode(Hex.toBytes(log.topics(1)), 0).value
    val to = event.indexed.type2.decode(Hex.toBytes(log.topics(2)), 0).value
    val value = decodedData
    Transfer(from, to, value)
  }
}

