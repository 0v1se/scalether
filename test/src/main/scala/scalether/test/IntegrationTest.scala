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
  val abi = "[{\"name\":\"state\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"constant\":true},{\"name\":\"SimpleEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"string\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"AddressEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"MixedEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\",\"indexed\":true},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false},{\"name\":\"test\",\"type\":\"address\",\"indexed\":true}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"RateEvent\",\"inputs\":[{\"name\":\"token\",\"type\":\"address\",\"indexed\":false},{\"name\":\"value\",\"type\":\"uint256\",\"indexed\":false}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"setState\",\"type\":\"function\",\"inputs\":[{\"name\":\"_state\",\"type\":\"uint256\"}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"setRates\",\"type\":\"function\",\"inputs\":[{\"name\":\"rates\",\"type\":\"tuple[]\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"setRate\",\"type\":\"function\",\"inputs\":[{\"name\":\"_rate\",\"type\":\"tuple\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"getRate\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"tuple\",\"components\":[{\"name\":\"token\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}]}],\"payable\":false,\"constant\":false},{\"name\":\"emitSimpleEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"string\"}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"emitAddressEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"string\"}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"emitMixedEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"string\"},{\"name\":\"test\",\"type\":\"address\"}],\"outputs\":[],\"payable\":false,\"constant\":false}]"
  val bin = "0x6060604052341561000f57600080fd5b610abb8061001e6000396000f30060606040526004361061008e576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806329dc7efe14610093578063679aefce146100b557806375e8660d146100de578063a9e966b714610100578063b710cc7c14610122578063c19d93fb14610144578063c4be9b301461016d578063d1c58fbd1461018f575b600080fd5b341561009e57600080fd5b6100b360046100ae90369061078c565b6101b1565b005b34156100c057600080fd5b6100c861024f565b6040516100d59190610918565b60405180910390f35b34156100e957600080fd5b6100fe60046100f99036906106e4565b6102c9565b005b341561010b57600080fd5b610120600461011b903690610821565b610333565b005b341561012d57600080fd5b610142600461013d90369061074b565b61033d565b005b341561014f57600080fd5b6101576103d2565b6040516101649190610933565b60405180910390f35b341561017857600080fd5b61018d60046101889036906107f8565b6103d8565b005b341561019a57600080fd5b6101af60046101aa903690610690565b610473565b005b816040518082805190602001908083835b6020831015156101e757805182526020820191506020810190506020830392506101c2565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390207fdf07c14ba7c542332df3cebcac73245d796909b2be2b2aed13f40d5b23136cd08260405161024391906108f6565b60405180910390a25050565b6102576104c5565b60016040805190810160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001600182015481525050905090565b8073ffffffffffffffffffffffffffffffffffffffff168373ffffffffffffffffffffffffffffffffffffffff167f0f5e2a2282931e0c4b5f2319a04deb79518c3127921c3f70d2fe9306563dd1678460405161032691906108f6565b60405180910390a3505050565b8060008190555050565b60008082519150600090505b818110156103cd577ff6cc106d4ff63d31aa62a665afe317068b92fc2bda4688afe33635cfff444749838281518110151561038057fe5b9060200190602002015160000151848381518110151561039c57fe5b90602001906020020151602001516040516103b89291906108cd565b60405180910390a18080600101915050610349565b505050565b60005481565b80600160008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550602082015181600101559050507ff6cc106d4ff63d31aa62a665afe317068b92fc2bda4688afe33635cfff444749816000015182602001516040516104689291906108cd565b60405180910390a150565b8173ffffffffffffffffffffffffffffffffffffffff167f2d1926fdeec1e1c3ef245ded9ff04522b3e5e14f0a57a12efe35949d72bf661e826040516104b991906108f6565b60405180910390a25050565b6040805190810160405280600073ffffffffffffffffffffffffffffffffffffffff168152602001600081525090565b60006105018235610a04565b905092915050565b600082601f830112151561051c57600080fd5b813561052f61052a8261097b565b61094e565b9150818183526020840193506020810190508385604084028201111561055457600080fd5b60005b83811015610584578161056a88826105e4565b845260208401935060408301925050600181019050610557565b5050505092915050565b600082601f83011215156105a157600080fd5b81356105b46105af826109a3565b61094e565b915080825260208301602083018583830111156105d057600080fd5b6105db838284610a2e565b50505092915050565b6000604082840312156105f657600080fd5b610600604061094e565b90506000610610848285016104f5565b60008301525060206106248482850161067c565b60208301525092915050565b60006040828403121561064257600080fd5b61064c604061094e565b9050600061065c848285016104f5565b60008301525060206106708482850161067c565b60208301525092915050565b60006106888235610a24565b905092915050565b600080604083850312156106a357600080fd5b60006106b1858286016104f5565b925050602083013567ffffffffffffffff8111156106ce57600080fd5b6106da8582860161058e565b9150509250929050565b6000806000606084860312156106f957600080fd5b6000610707868287016104f5565b935050602084013567ffffffffffffffff81111561072457600080fd5b6107308682870161058e565b9250506040610741868287016104f5565b9150509250925092565b60006020828403121561075d57600080fd5b600082013567ffffffffffffffff81111561077757600080fd5b61078384828501610509565b91505092915050565b6000806040838503121561079f57600080fd5b600083013567ffffffffffffffff8111156107b957600080fd5b6107c58582860161058e565b925050602083013567ffffffffffffffff8111156107e257600080fd5b6107ee8582860161058e565b9150509250929050565b60006040828403121561080a57600080fd5b600061081884828501610630565b91505092915050565b60006020828403121561083357600080fd5b60006108418482850161067c565b91505092915050565b610853816109da565b82525050565b6000610864826109cf565b808452610878816020860160208601610a3d565b61088181610a70565b602085010191505092915050565b6040820160008201516108a5600085018261084a565b5060208201516108b860208501826108be565b50505050565b6108c7816109fa565b82525050565b60006040820190506108e2600083018561084a565b6108ef60208301846108be565b9392505050565b600060208201905081810360008301526109108184610859565b905092915050565b600060408201905061092d600083018461088f565b92915050565b600060208201905061094860008301846108be565b92915050565b6000604051905081810181811067ffffffffffffffff8211171561097157600080fd5b8060405250919050565b600067ffffffffffffffff82111561099257600080fd5b602082029050602081019050919050565b600067ffffffffffffffff8211156109ba57600080fd5b601f19601f8301169050602081019050919050565b600081519050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000819050919050565b82818337600083830152505050565b60005b83811015610a5b578082015181840152602081019050610a40565b83811115610a6a576000848401525b50505050565b6000601f19601f83011690509190505600a265627a7a72305820a0ff36ec3f12f79595d8b0b7e0bb6de09a8a303ed11e3ad594bfba37917ff96d6c6578706572696d656e74616cf50037"

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

