package scalether.test

import cats.implicits._
import cats.{Functor, Monad}
import scalether.abi._
import scalether.abi.tuple._
import scalether.contract._
import scalether.core.TransactionSender
import scalether.core.request.Transaction
import scalether.util.Hex
import scalether.util.transaction.TransactionService

import scala.language.higherKinds

class Events[F[_] : Functor](address: String, sender: TransactionSender[F]) extends Contract[F](address, sender) {
  def callEmitEvent(topic: String, value: String): F[Unit] =
    call(Signature("emitEvent", Tuple2Type(StringType, StringType), UnitType), (topic, value))

  def emitEvent(topic: String, value: String): F[String] =
    sendTransaction(Signature("emitEvent", Tuple2Type(StringType, StringType), UnitType), (topic, value))

  def callEmitAddressEvent(topic: String, value: String): F[Unit] =
    call(Signature("emitAddressEvent", Tuple2Type(AddressType, StringType), UnitType), (topic, value))

  def emitAddressEvent(topic: String, value: String): F[String] =
    sendTransaction(Signature("emitAddressEvent", Tuple2Type(AddressType, StringType), UnitType), (topic, value))

}

object Events extends ContractObject {
  val name = "Events"
  val bin = "0x6060604052341561000f57600080fd5b5b6103278061001f6000396000f300606060405263ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663cbf6b6fe8114610048578063d1c58fbd146100dd575b600080fd5b341561005357600080fd5b6100db60046024813581810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284378201915050505050509190803590602001908201803590602001908080601f01602080910402602001604051908101604052818152929190602084018383808284375094965061014b95505050505050565b005b34156100e857600080fd5b6100db6004803573ffffffffffffffffffffffffffffffffffffffff169060446024803590810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284375094965061024695505050505050565b005b816040518082805190602001908083835b6020831061017c57805182525b601f19909201916020918201910161015c565b6001836020036101000a038019825116818451161790925250505091909101925060409150505180910390207f39b8d23135cdeca3f85b347e5285f40c9b1de764cf9f8126e7f3b34d77ff0cf08260405160208082528190810183818151815260200191508051906020019080838360005b838110156102075780820151818401525b6020016101ee565b50505050905090810190601f1680156102345780820380516001836020036101000a031916815260200191505b509250505060405180910390a25b5050565b8173ffffffffffffffffffffffffffffffffffffffff167f2d1926fdeec1e1c3ef245ded9ff04522b3e5e14f0a57a12efe35949d72bf661e8260405160208082528190810183818151815260200191508051906020019080838360005b838110156102075780820151818401525b6020016101ee565b50505050905090810190601f1680156102345780820380516001836020036101000a031916815260200191505b509250505060405180910390a25b50505600a165627a7a72305820566b97bd7d34cb705cdab1d5cac3a38266b02685fae44b2981678ad3594a57d50029"
  val abi = "[{\"name\":\"emitEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"string\",\"components\":null},{\"name\":\"value\",\"type\":\"string\",\"components\":null}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"emitAddressEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\",\"components\":null},{\"name\":\"value\",\"type\":\"string\",\"components\":null}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"Event\",\"inputs\":[{\"name\":\"topic\",\"type\":\"string\",\"indexed\":true,\"components\":null},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false,\"components\":null}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"AddressEvent\",\"inputs\":[{\"name\":\"topic\",\"type\":\"address\",\"indexed\":true,\"components\":null},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false,\"components\":null}],\"anonymous\":false,\"type\":\"event\"}]"
  val constructor = UnitType

  def encodeArgs: Array[Byte] =
    constructor.encode()

  def deployTransactionData: String =
    bin + Hex.bytesToHex(encodeArgs)

  def deploy[F[_] : Functor](sender: TransactionSender[F]): F[String] =
    sender.sendTransaction(Transaction(data = Some(deployTransactionData)))

  def deployAndWait[F[_] : Monad](sender: TransactionSender[F], service: TransactionService[F])
                                 : F[Events[F]] =
    deploy(sender)
      .flatMap(hash => service.waitForTransaction(hash))
      .map(receipt => new Events[F](receipt.contractAddress, sender))

}