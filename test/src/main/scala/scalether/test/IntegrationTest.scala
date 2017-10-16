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
import scalether.extra.transaction._
import scalether.util.Hex

import scala.language.higherKinds

class IntegrationTest[F[_]](address: Address, sender: TransactionSender[F])(implicit f: Functor[F])
  extends Contract[F](address, sender) {

  def callEmitSimpleEvent(topic: String, value: String): F[Unit] =
    call(Signature("emitSimpleEvent", Tuple2Type(StringType, StringType), UnitType), (topic, value))

  def emitSimpleEvent(topic: String, value: String): F[String] =
    sendTransaction(Signature("emitSimpleEvent", Tuple2Type(StringType, StringType), UnitType), (topic, value))

  def callEmitMixedEvent(topic: Address, value: String, test: Address): F[Unit] =
    call(Signature("emitMixedEvent", Tuple3Type(AddressType, StringType, AddressType), UnitType), (topic, value, test))

  def emitMixedEvent(topic: Address, value: String, test: Address): F[String] =
    sendTransaction(Signature("emitMixedEvent", Tuple3Type(AddressType, StringType, AddressType), UnitType), (topic, value, test))

  def callSetState(_state: BigInteger): F[Unit] =
    call(Signature("setState", Tuple1Type(Uint256Type), UnitType), _state)

  def setState(_state: BigInteger): F[String] =
    sendTransaction(Signature("setState", Tuple1Type(Uint256Type), UnitType), _state)

  def state: F[BigInteger] =
    call(Signature("state", UnitType, Tuple1Type(Uint256Type)), ())

  def callEmitAddressEvent(topic: Address, value: String): F[Unit] =
    call(Signature("emitAddressEvent", Tuple2Type(AddressType, StringType), UnitType), (topic, value))

  def emitAddressEvent(topic: Address, value: String): F[String] =
    sendTransaction(Signature("emitAddressEvent", Tuple2Type(AddressType, StringType), UnitType), (topic, value))

}

object IntegrationTest extends ContractObject {
  val name = "IntegrationTest"
  val abi = "[{\"name\":\"emitSimpleEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"string\",\"components\":null},{\"name\":\"value\",\"type\":\"string\",\"components\":null}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"emitMixedEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\",\"components\":null},{\"name\":\"value\",\"type\":\"string\",\"components\":null},{\"name\":\"test\",\"type\":\"address\",\"components\":null}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"setState\",\"type\":\"function\",\"inputs\":[{\"name\":\"_state\",\"type\":\"uint256\",\"components\":null}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"state\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"uint256\",\"components\":null}],\"payable\":false,\"constant\":true},{\"name\":\"emitAddressEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\",\"components\":null},{\"name\":\"value\",\"type\":\"string\",\"components\":null}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"SimpleEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"string\",\"indexed\":true,\"components\":null},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false,\"components\":null}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"AddressEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\",\"indexed\":true,\"components\":null},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false,\"components\":null}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"MixedEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\",\"indexed\":true,\"components\":null},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false,\"components\":null},{\"name\":\"test\",\"type\":\"address\",\"indexed\":true,\"components\":null}],\"anonymous\":false,\"type\":\"event\"}]"
  val bin = "0x6060604052341561000f57600080fd5b5b6104998061001f6000396000f300606060405263ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166329dc7efe811461006957806375e8660d146100fe578063a9e966b71461016a578063c19d93fb14610182578063d1c58fbd146101a7575b600080fd5b341561007457600080fd5b6100fc60046024813581810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284378201915050505050509190803590602001908201803590602001908080601f01602080910402602001604051908101604052818152929190602084018383808284375094965061020895505050505050565b005b341561010957600080fd5b6100fc60048035600160a060020a03169060446024803590810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284375094965050509235600160a060020a03169250610303915050565b005b341561017557600080fd5b6100fc6004356103b6565b005b341561018d57600080fd5b6101956103bf565b60405190815260200160405180910390f35b34156101b257600080fd5b6100fc60048035600160a060020a03169060446024803590810190830135806020601f820181900481020160405190810160405281815292919060208401838380828437509496506103c595505050505050565b005b816040518082805190602001908083835b6020831061023957805182525b601f199092019160209182019101610219565b6001836020036101000a038019825116818451161790925250505091909101925060409150505180910390207fdf07c14ba7c542332df3cebcac73245d796909b2be2b2aed13f40d5b23136cd08260405160208082528190810183818151815260200191508051906020019080838360005b838110156102c45780820151818401525b6020016102ab565b50505050905090810190601f1680156102f15780820380516001836020036101000a031916815260200191505b509250505060405180910390a25b5050565b80600160a060020a031683600160a060020a03167f0f5e2a2282931e0c4b5f2319a04deb79518c3127921c3f70d2fe9306563dd1678460405160208082528190810183818151815260200191508051906020019080838360005b838110156103765780820151818401525b60200161035d565b50505050905090810190601f1680156103a35780820380516001836020036101000a031916815260200191505b509250505060405180910390a35b505050565b60008190555b50565b60005481565b81600160a060020a03167f2d1926fdeec1e1c3ef245ded9ff04522b3e5e14f0a57a12efe35949d72bf661e8260405160208082528190810183818151815260200191508051906020019080838360005b838110156102c45780820151818401525b6020016102ab565b50505050905090810190601f1680156102f15780820380516001836020036101000a031916815260200191505b509250505060405180910390a25b50505600a165627a7a723058203a1737b622aece697726d58b464d5a2c9be35be1b0344a563f95c6fc1029630d0029"

  val constructor = UnitType

  def encodeArgs: Array[Byte] =
    constructor.encode()

  def deployTransactionData: String =
    bin + Hex.to(encodeArgs)

  def deploy[F[_]](sender: TransactionSender[F])(implicit f: Functor[F]): F[String] =
    sender.sendTransaction(Transaction(data = deployTransactionData))

  def deployAndWait[F[_]](sender: TransactionSender[F], poller: TransactionPoller[F])(implicit m: Monad[F]) : F[IntegrationTest[F]] =
      poller.waitForTransaction(deploy(sender))
      .map(receipt => new IntegrationTest[F](receipt.contractAddress, sender))
}

case class SimpleEvent(topic: Word, value: String)

object SimpleEvent {
  val event = Event("SimpleEvent", List(StringType, StringType), Tuple1Type(StringType), Tuple1Type(StringType))

  def apply(log: Log): SimpleEvent = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val topic = Word(log.topics(1))
    val value = decodedData
    SimpleEvent(topic, value)
  }
}

case class AddressEvent(topic: Address, value: String)

object AddressEvent {
  val event = Event("AddressEvent", List(AddressType, StringType), Tuple1Type(AddressType), Tuple1Type(StringType))

  def apply(log: Log): AddressEvent = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val topic = event.indexed.type1.decode(Hex.toBytes(log.topics(1)), 0).value
    val value = decodedData
    AddressEvent(topic, value)
  }
}

case class MixedEvent(topic: Address, test: Address, value: String)

object MixedEvent {
  val event = Event("MixedEvent", List(AddressType, StringType, AddressType), Tuple2Type(AddressType, AddressType), Tuple1Type(StringType))

  def apply(log: Log): MixedEvent = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val topic = event.indexed.type1.decode(Hex.toBytes(log.topics(1)), 0).value
    val test = event.indexed.type2.decode(Hex.toBytes(log.topics(2)), 0).value
    val value = decodedData
    MixedEvent(topic, test, value)
  }
}

