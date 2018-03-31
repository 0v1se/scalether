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

  def setRates(rates: Array[(Address, BigInteger)]): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, Signature("setRates", Tuple1Type(VarArrayType(Tuple2Type(AddressType, Uint256Type))), UnitType), rates, sender)

  def emitSimpleEvent(topic: String, value: String): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, Signature("emitSimpleEvent", Tuple2Type(StringType, StringType), UnitType), (topic, value), sender)

  def emitAddressEvent(topic: Address, value: String): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, Signature("emitAddressEvent", Tuple2Type(AddressType, StringType), UnitType), (topic, value), sender)

  def emitMixedEvent(topic: Address, value: String, test: Address): PreparedTransaction[F, Unit] =
    PreparedTransaction(address, Signature("emitMixedEvent", Tuple3Type(AddressType, StringType, AddressType), UnitType), (topic, value, test), sender)

}

object IntegrationTest extends ContractObject {
  val name = "IntegrationTest"
  val abi = "[{\"name\":\"state\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"constant\":true},{\"name\":\"SimpleEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"string\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"AddressEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"MixedEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false},{\"name\":\"test\",\"type\":\"address\",\"indexed\":true}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"RateEvent\",\"inputs\":[{\"name\":\"token\",\"type\":\"address\",\"indexed\":false},{\"name\":\"value\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"setState\",\"type\":\"function\",\"inputs\":[{\"name\":\"_state\",\"type\":\"uint256\"}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"setRates\",\"type\":\"function\",\"inputs\":[{\"name\":\"rates\",\"type\":\"tuple[]\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"emitSimpleEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"string\"}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"emitAddressEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"string\"}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"emitMixedEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"string\"},{\"name\":\"test\",\"type\":\"address\"}],\"outputs\":[],\"payable\":false,\"constant\":false}]"
  val bin = "0x6060604052341561000f57600080fd5b6108568061001e6000396000f300606060405260043610610078576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806329dc7efe1461007d57806375e8660d1461009f578063a9e966b7146100c1578063b710cc7c146100e3578063c19d93fb14610105578063d1c58fbd1461012e575b600080fd5b341561008857600080fd5b61009d600461009890369061059a565b610150565b005b34156100aa57600080fd5b6100bf60046100ba9036906104f2565b6101ee565b005b34156100cc57600080fd5b6100e160046100dc903690610606565b610258565b005b34156100ee57600080fd5b61010360046100fe903690610559565b610262565b005b341561011057600080fd5b6101186102f7565b60405161012591906106ce565b60405180910390f35b341561013957600080fd5b61014e600461014990369061049e565b6102fd565b005b816040518082805190602001908083835b6020831015156101865780518252602082019150602081019050602083039250610161565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390207fdf07c14ba7c542332df3cebcac73245d796909b2be2b2aed13f40d5b23136cd0826040516101e291906106ac565b60405180910390a25050565b8073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff167f0f5e2a2282931e0c4b5f2319a04deb79518c3127921c3f70d2fe9306563dd1678460405161024b91906106ac565b60405180910390a3505050565b8060008190555050565b60008082519150600090505b818110156102f2577ff6cc106d4ff63d31aa62a665afe317068b92fc2bda4688afe33635cfff44474983828151811015156102a557fe5b906020019060200201516000015184838151811015156102c157fe5b90602001906020020151602001516040516102dd929190610683565b60405180910390a1808060010191505061026e565b505050565b60005481565b8173ffffffffffffffffffffffffffffffffffffffff167f2d1926fdeec1e1c3ef245ded9ff04522b3e5e14f0a57a12efe35949d72bf661e8260405161034391906106ac565b60405180910390a25050565b600061035b823561079f565b905092915050565b600082601f830112151561037657600080fd5b813561038961038482610716565b6106e9565b915081818352602084019350602081019050838560408402820111156103ae57600080fd5b60005b838110156103de57816103c4888261043e565b8452602084019350604083019250506001810190506103b1565b5050505092915050565b600082601f83011215156103fb57600080fd5b813561040e6104098261073e565b6106e9565b9150808252602083016020830185838301111561042a57600080fd5b6104358382846107c9565b50505092915050565b60006040828403121561045057600080fd5b61045a60406106e9565b9050600061046a8482850161034f565b600083015250602061047e8482850161048a565b60208301525092915050565b600061049682356107bf565b905092915050565b600080604083850312156104b157600080fd5b60006104bf8582860161034f565b925050602083013567ffffffffffffffff8111156104dc57600080fd5b6104e8858286016103e8565b9150509250929050565b60008060006060848603121561050757600080fd5b60006105158682870161034f565b935050602084013567ffffffffffffffff81111561053257600080fd5b61053e868287016103e8565b925050604061054f8682870161034f565b9150509250925092565b60006020828403121561056b57600080fd5b600082013567ffffffffffffffff81111561058557600080fd5b61059184828501610363565b91505092915050565b600080604083850312156105ad57600080fd5b600083013567ffffffffffffffff8111156105c757600080fd5b6105d3858286016103e8565b925050602083013567ffffffffffffffff8111156105f057600080fd5b6105fc858286016103e8565b9150509250929050565b60006020828403121561061857600080fd5b60006106268482850161048a565b91505092915050565b61063881610775565b82525050565b60006106498261076a565b80845261065d8160208601602086016107d8565b6106668161080b565b602085010191505092915050565b61067d81610795565b82525050565b6000604082019050610698600083018561062f565b6106a56020830184610674565b9392505050565b600060208201905081810360008301526106c6818461063e565b905092915050565b60006020820190506106e36000830184610674565b92915050565b6000604051905081810181811067ffffffffffffffff8211171561070c57600080fd5b8060405250919050565b600067ffffffffffffffff82111561072d57600080fd5b602082029050602081019050919050565b600067ffffffffffffffff82111561075557600080fd5b601f19601f8301169050602081019050919050565b600081519050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b82818337600083830152505050565b60005b838110156107f65780820151818401526020810190506107db565b83811115610805576000848401525b50505050565b6000601f19601f83011690509190505600a265627a7a72305820bc86fbafd32e9dae71455683295c3139e1cc6f27b5d5c2bf4d8ba1987aa501686c6578706572696d656e74616cf50037"

  val constructor = UnitType

  def encodeArgs: Array[Byte] =
    constructor.encode()

  def deployTransactionData: Array[Byte] =
    Hex.toBytes(bin) ++ encodeArgs

  def deploy[F[_]](sender: TransactionSender[F])(implicit f: Functor[F]): F[String] =
    sender.sendTransaction(request.Transaction(data = deployTransactionData))

  def deployAndWait[F[_]](sender: TransactionSender[F], poller: TransactionPoller[F])(implicit m: MonadError[F, Throwable]): F[IntegrationTest[F]] =
      poller.waitForTransaction(deploy(sender))
      .map(receipt => new IntegrationTest[F](receipt.contractAddress, sender))
}

case class SimpleEvent(topic: Word, value: String)

object SimpleEvent {
  val event = Event("SimpleEvent", List(StringType, StringType), Tuple1Type(StringType), Tuple1Type(StringType))

  @annotation.varargs def filter(fromBlock: String, toBlock: String, addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(Word.apply(event.id))), address = addresses.toList, fromBlock = fromBlock, toBlock = toBlock)

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(Word.apply(event.id))), address = addresses.toList)

  def apply(log: response.Log): SimpleEvent = {
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

  @annotation.varargs def filter(fromBlock: String, toBlock: String, addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(Word.apply(event.id))), address = addresses.toList, fromBlock = fromBlock, toBlock = toBlock)

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(Word.apply(event.id))), address = addresses.toList)

  def apply(log: response.Log): AddressEvent = {
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

  @annotation.varargs def filter(fromBlock: String, toBlock: String, addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(Word.apply(event.id))), address = addresses.toList, fromBlock = fromBlock, toBlock = toBlock)

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(Word.apply(event.id))), address = addresses.toList)

  def apply(log: response.Log): MixedEvent = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val topic = event.indexed.type1.decode(Hex.toBytes(log.topics(1)), 0).value
    val test = event.indexed.type2.decode(Hex.toBytes(log.topics(2)), 0).value
    val value = decodedData
    MixedEvent(topic, test, value)
  }
}

case class RateEvent(token: Address, value: BigInteger)

object RateEvent {
  val event = Event("RateEvent", List(AddressType, Uint256Type), UnitType, Tuple2Type(AddressType, Uint256Type))

  @annotation.varargs def filter(fromBlock: String, toBlock: String, addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(Word.apply(event.id))), address = addresses.toList, fromBlock = fromBlock, toBlock = toBlock)

  @annotation.varargs def filter(addresses: Address*): LogFilter =
    LogFilter(topics = List(SimpleTopicFilter(Word.apply(event.id))), address = addresses.toList)

  def apply(log: response.Log): RateEvent = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val token = decodedData._1
    val value = decodedData._2
    RateEvent(token, value)
  }
}

