package scalether.test

import java.math.BigInteger

import cats.{Functor, MonadError}
import cats.implicits._
import scalether.abi._
import scalether.abi.array._
import scalether.abi.tuple._
import scalether.contract._
import scalether.domain._
import scalether.domain.request._
import scalether.transaction._
import scalether.util.Hex

import scala.language.higherKinds

class IntegrationTest[F[_]](address: Address, sender: TransactionSender[F])(implicit f: MonadError[F, Throwable])
  extends Contract[F](address, sender) {

  def state: F[BigInteger] =
    PreparedTransaction(address, Signature("state", UnitType, Tuple1Type(Uint256Type)), (), sender).call()

  def setState(_state: BigInteger): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, Signature("setState", Tuple1Type(Uint256Type), UnitType), _state, sender)

  def checkStructsWithString(structs: Array[(String, BigInteger)]): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, Signature("checkStructsWithString", Tuple1Type(VarArrayType(Tuple2Type(StringType, Uint256Type))), UnitType), structs, sender)

  def setRates(rates: Array[(Address, BigInteger)]): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, Signature("setRates", Tuple1Type(VarArrayType(Tuple2Type(AddressType, Uint256Type))), UnitType), rates, sender)

  def setRate(_rate: (Address, BigInteger)): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, Signature("setRate", Tuple1Type(Tuple2Type(AddressType, Uint256Type)), UnitType), _rate, sender)

  def getRate: PreparedTransaction[F, (Address, BigInteger)] =
    PreparedTransaction(address, Signature("getRate", UnitType, Tuple1Type(Tuple2Type(AddressType, Uint256Type))), (), sender)

  def emitSimpleEvent(topic: String, value: String): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, Signature("emitSimpleEvent", Tuple2Type(StringType, StringType), UnitType), (topic, value), sender)

  def emitAddressEvent(topic: Address, value: String): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, Signature("emitAddressEvent", Tuple2Type(AddressType, StringType), UnitType), (topic, value), sender)

  def emitMixedEvent(topic: Address, value: String, test: Address): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, Signature("emitMixedEvent", Tuple3Type(AddressType, StringType, AddressType), UnitType), (topic, value, test), sender)

}

object IntegrationTest extends ContractObject {
  val name = "IntegrationTest"
  val abi = "[{\"name\":\"state\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"constant\":true},{\"name\":\"SimpleEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"string\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"AddressEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"MixedEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false},{\"name\":\"test\",\"type\":\"address\",\"indexed\":true}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"RateEvent\",\"inputs\":[{\"name\":\"token\",\"type\":\"address\",\"indexed\":false},{\"name\":\"value\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"StringEvent\",\"inputs\":[{\"name\":\"str\",\"type\":\"string\",\"indexed\":false},{\"name\":\"value\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"TestEvent\",\"inputs\":[{\"name\":\"value1\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"TestEvent\",\"inputs\":[{\"name\":\"value1\",\"type\":\"uint256\",\"indexed\":false},{\"name\":\"value2\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"TestEvent\",\"inputs\":[{\"name\":\"value1\",\"type\":\"uint256\",\"indexed\":false},{\"name\":\"value2\",\"type\":\"uint256\",\"indexed\":false},{\"name\":\"value3\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"setState\",\"type\":\"function\",\"inputs\":[{\"name\":\"_state\",\"type\":\"uint256\"}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"checkStructsWithString\",\"type\":\"function\",\"inputs\":[{\"name\":\"structs\",\"type\":\"tuple[]\",\"components\":[{\"name\":\"str\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"setRates\",\"type\":\"function\",\"inputs\":[{\"name\":\"rates\",\"type\":\"tuple[]\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"setRate\",\"type\":\"function\",\"inputs\":[{\"name\":\"_rate\",\"type\":\"tuple\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"getRate\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"tuple\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"payable\":false,\"constant\":false},{\"name\":\"emitSimpleEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"string\"}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"emitAddressEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"string\"}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"emitMixedEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"string\"},{\"name\":\"test\",\"type\":\"address\"}],\"outputs\":[],\"payable\":false,\"constant\":false}]"
  val bin = "0x608060405234801561001057600080fd5b50610de8806100206000396000f300608060405260043610610099576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806329dc7efe1461009e5780633cb7bce8146100c7578063679aefce146100f057806375e8660d1461011b578063a9e966b714610144578063b710cc7c1461016d578063c19d93fb14610196578063c4be9b30146101c1578063d1c58fbd146101ea575b600080fd5b3480156100aa57600080fd5b506100c560048036036100c091908101906109f4565b610213565b005b3480156100d357600080fd5b506100ee60048036036100e991908101906109b3565b6102b1565b005b3480156100fc57600080fd5b50610105610346565b6040516101129190610be6565b60405180910390f35b34801561012757600080fd5b50610142600480360361013d919081019061090b565b6103c0565b005b34801561015057600080fd5b5061016b60048036036101669190810190610a89565b61042a565b005b34801561017957600080fd5b50610194600480360361018f9190810190610972565b610434565b005b3480156101a257600080fd5b506101ab6104c9565b6040516101b89190610c01565b60405180910390f35b3480156101cd57600080fd5b506101e860048036036101e39190810190610a60565b6104cf565b005b3480156101f657600080fd5b50610211600480360361020c91908101906108b7565b61056a565b005b816040518082805190602001908083835b6020831015156102495780518252602082019150602081019050602083039250610224565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390207fdf07c14ba7c542332df3cebcac73245d796909b2be2b2aed13f40d5b23136cd0826040516102a59190610b94565b60405180910390a25050565b60008082519150600090505b81811015610341577f6fc10800b7deda6aea9a2a89968fd690847169903420807806184ae95e93693383828151811015156102f457fe5b9060200190602002015160000151848381518110151561031057fe5b906020019060200201516020015160405161032c929190610bb6565b60405180910390a180806001019150506102bd565b505050565b61034e6105bc565b60016040805190810160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001600182015481525050905090565b8073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff167f0f5e2a2282931e0c4b5f2319a04deb79518c3127921c3f70d2fe9306563dd1678460405161041d9190610b94565b60405180910390a3505050565b8060008190555050565b60008082519150600090505b818110156104c4577ff6cc106d4ff63d31aa62a665afe317068b92fc2bda4688afe33635cfff444749838281518110151561047757fe5b9060200190602002015160000151848381518110151561049357fe5b90602001906020020151602001516040516104af929190610b6b565b60405180910390a18080600101915050610440565b505050565b60005481565b80600160008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550602082015181600101559050507ff6cc106d4ff63d31aa62a665afe317068b92fc2bda4688afe33635cfff4447498160000151826020015160405161055f929190610b6b565b60405180910390a150565b8173ffffffffffffffffffffffffffffffffffffffff167f2d1926fdeec1e1c3ef245ded9ff04522b3e5e14f0a57a12efe35949d72bf661e826040516105b09190610b94565b60405180910390a25050565b6040805190810160405280600073ffffffffffffffffffffffffffffffffffffffff168152602001600081525090565b60006105f88235610d31565b905092915050565b600082601f830112151561061357600080fd5b813561062661062182610c49565b610c1c565b9150818183526020840193506020810190508385604084028201111561064b57600080fd5b60005b8381101561067b578161066188826107a7565b84526020840193506040830192505060018101905061064e565b5050505092915050565b600082601f830112151561069857600080fd5b81356106ab6106a682610c71565b610c1c565b9150818183526020840193506020810190508360005b838110156106f157813586016106d7888261083f565b8452602084019350602083019250506001810190506106c1565b5050505092915050565b600082601f830112151561070e57600080fd5b813561072161071c82610c99565b610c1c565b9150808252602083016020830185838301111561073d57600080fd5b610748838284610d5b565b50505092915050565b600082601f830112151561076457600080fd5b813561077761077282610cc5565b610c1c565b9150808252602083016020830185838301111561079357600080fd5b61079e838284610d5b565b50505092915050565b6000604082840312156107b957600080fd5b6107c36040610c1c565b905060006107d3848285016105ec565b60008301525060206107e7848285016108a3565b60208301525092915050565b60006040828403121561080557600080fd5b61080f6040610c1c565b9050600061081f848285016105ec565b6000830152506020610833848285016108a3565b60208301525092915050565b60006040828403121561085157600080fd5b61085b6040610c1c565b9050600082013567ffffffffffffffff81111561087757600080fd5b610883848285016106fb565b6000830152506020610897848285016108a3565b60208301525092915050565b60006108af8235610d51565b905092915050565b600080604083850312156108ca57600080fd5b60006108d8858286016105ec565b925050602083013567ffffffffffffffff8111156108f557600080fd5b61090185828601610751565b9150509250929050565b60008060006060848603121561092057600080fd5b600061092e868287016105ec565b935050602084013567ffffffffffffffff81111561094b57600080fd5b61095786828701610751565b9250506040610968868287016105ec565b9150509250925092565b60006020828403121561098457600080fd5b600082013567ffffffffffffffff81111561099e57600080fd5b6109aa84828501610600565b91505092915050565b6000602082840312156109c557600080fd5b600082013567ffffffffffffffff8111156109df57600080fd5b6109eb84828501610685565b91505092915050565b60008060408385031215610a0757600080fd5b600083013567ffffffffffffffff811115610a2157600080fd5b610a2d85828601610751565b925050602083013567ffffffffffffffff811115610a4a57600080fd5b610a5685828601610751565b9150509250929050565b600060408284031215610a7257600080fd5b6000610a80848285016107f3565b91505092915050565b600060208284031215610a9b57600080fd5b6000610aa9848285016108a3565b91505092915050565b610abb81610d07565b82525050565b6000610acc82610cfc565b808452610ae0816020860160208601610d6a565b610ae981610d9d565b602085010191505092915050565b6000610b0282610cf1565b808452610b16816020860160208601610d6a565b610b1f81610d9d565b602085010191505092915050565b604082016000820151610b436000850182610ab2565b506020820151610b566020850182610b5c565b50505050565b610b6581610d27565b82525050565b6000604082019050610b806000830185610ab2565b610b8d6020830184610b5c565b9392505050565b60006020820190508181036000830152610bae8184610ac1565b905092915050565b60006040820190508181036000830152610bd08185610af7565b9050610bdf6020830184610b5c565b9392505050565b6000604082019050610bfb6000830184610b2d565b92915050565b6000602082019050610c166000830184610b5c565b92915050565b6000604051905081810181811067ffffffffffffffff82111715610c3f57600080fd5b8060405250919050565b600067ffffffffffffffff821115610c6057600080fd5b602082029050602081019050919050565b600067ffffffffffffffff821115610c8857600080fd5b602082029050602081019050919050565b600067ffffffffffffffff821115610cb057600080fd5b601f19601f8301169050602081019050919050565b600067ffffffffffffffff821115610cdc57600080fd5b601f19601f8301169050602081019050919050565b600081519050919050565b600081519050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b82818337600083830152505050565b60005b83811015610d88578082015181840152602081019050610d6d565b83811115610d97576000848401525b50505050565b6000601f19601f83011690509190505600a265627a7a72305820dbf9dc9c37366591ad3e46bb3b2152b1795e8cf566a8201478208b2ace6dc3146c6578706572696d656e74616cf50037"

  val constructor = UnitType

  def encodeArgs: Array[Byte] =
    constructor.encode()

  def deployTransactionData: Binary =
    Binary(Hex.toBytes(bin) ++ encodeArgs)

  def deploy[F[_]](sender: TransactionSender[F])(implicit f: Functor[F]): F[Word] =
    sender.sendTransaction(request.Transaction(data = deployTransactionData))

  def deployAndWait[F[_]](sender: TransactionSender[F], poller: TransactionPoller[F])(implicit m: MonadError[F, Throwable]): F[IntegrationTest[F]] =
      poller.waitForTransaction(deploy(sender))
      .map(receipt => new IntegrationTest[F](receipt.contractAddress, sender))
}

case class SimpleEvent(topic: Word, value: String)

object SimpleEvent {
  val event = Event("SimpleEvent", List(StringType, StringType), Tuple1Type(StringType), Tuple1Type(StringType))

  @annotation.varargs def filter(fromBlock: String, toBlock: String, addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList, fromBlock = fromBlock, toBlock = toBlock)

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList)

  def apply(log: response.Log): SimpleEvent = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val topic = log.topics(1)
    val value = decodedData
    SimpleEvent(topic, value)
  }
}

case class AddressEvent(topic: Address, value: String)

object AddressEvent {
  val event = Event("AddressEvent", List(AddressType, StringType), Tuple1Type(AddressType), Tuple1Type(StringType))

  @annotation.varargs def filter(fromBlock: String, toBlock: String, addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList, fromBlock = fromBlock, toBlock = toBlock)

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList)

  def apply(log: response.Log): AddressEvent = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val topic = event.indexed.type1.decode(log.topics(1).bytes, 0).value
    val value = decodedData
    AddressEvent(topic, value)
  }
}

case class MixedEvent(topic: Address, test: Address, value: String)

object MixedEvent {
  val event = Event("MixedEvent", List(AddressType, StringType, AddressType), Tuple2Type(AddressType, AddressType), Tuple1Type(StringType))

  @annotation.varargs def filter(fromBlock: String, toBlock: String, addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList, fromBlock = fromBlock, toBlock = toBlock)

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList)

  def apply(log: response.Log): MixedEvent = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val topic = event.indexed.type1.decode(log.topics(1).bytes, 0).value
    val test = event.indexed.type2.decode(log.topics(2).bytes, 0).value
    val value = decodedData
    MixedEvent(topic, test, value)
  }
}

case class RateEvent(token: Address, value: BigInteger)

object RateEvent {
  val event = Event("RateEvent", List(AddressType, Uint256Type), UnitType, Tuple2Type(AddressType, Uint256Type))

  @annotation.varargs def filter(fromBlock: String, toBlock: String, addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList, fromBlock = fromBlock, toBlock = toBlock)

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList)

  def apply(log: response.Log): RateEvent = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val token = decodedData._1
    val value = decodedData._2
    RateEvent(token, value)
  }
}

case class StringEvent(str: String, value: BigInteger)

object StringEvent {
  val event = Event("StringEvent", List(StringType, Uint256Type), UnitType, Tuple2Type(StringType, Uint256Type))

  @annotation.varargs def filter(fromBlock: String, toBlock: String, addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList, fromBlock = fromBlock, toBlock = toBlock)

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList)

  def apply(log: response.Log): StringEvent = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val str = decodedData._1
    val value = decodedData._2
    StringEvent(str, value)
  }
}

case class TestEvent(value1: BigInteger)

object TestEvent {
  val event = Event("TestEvent", List(Uint256Type), UnitType, Tuple1Type(Uint256Type))

  @annotation.varargs def filter(fromBlock: String, toBlock: String, addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList, fromBlock = fromBlock, toBlock = toBlock)

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList)

  def apply(log: response.Log): TestEvent = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val value1 = decodedData
    TestEvent(value1)
  }
}

case class Test1Event(value1: BigInteger, value2: BigInteger)

object Test1Event {
  val event = Event("TestEvent", List(Uint256Type, Uint256Type), UnitType, Tuple2Type(Uint256Type, Uint256Type))

  @annotation.varargs def filter(fromBlock: String, toBlock: String, addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList, fromBlock = fromBlock, toBlock = toBlock)

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList)

  def apply(log: response.Log): Test1Event = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val value1 = decodedData._1
    val value2 = decodedData._2
    Test1Event(value1, value2)
  }
}

case class Test2Event(value1: BigInteger, value2: BigInteger, value3: BigInteger)

object Test2Event {
  val event = Event("TestEvent", List(Uint256Type, Uint256Type, Uint256Type), UnitType, Tuple3Type(Uint256Type, Uint256Type, Uint256Type))

  @annotation.varargs def filter(fromBlock: String, toBlock: String, addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList, fromBlock = fromBlock, toBlock = toBlock)

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(event.id)), address = addresses.toList)

  def apply(log: response.Log): Test2Event = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val value1 = decodedData._1
    val value2 = decodedData._2
    val value3 = decodedData._3
    Test2Event(value1, value2, value3)
  }
}

