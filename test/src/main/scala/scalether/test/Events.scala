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

}

object Events extends ContractObject {
  val name = "Events"
  val bin = "0x6060604052341561000f57600080fd5b5b6101f98061001f6000396000f300606060405263ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663cbf6b6fe811461003d575b600080fd5b341561004857600080fd5b6100d060046024813581810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405281815292919060208401838380828437509496506100d295505050505050565b005b816040518082805190602001908083835b6020831061010357805182525b601f1990920191602091820191016100e3565b6001836020036101000a038019825116818451161790925250505091909101925060409150505180910390207f39b8d23135cdeca3f85b347e5285f40c9b1de764cf9f8126e7f3b34d77ff0cf08260405160208082528190810183818151815260200191508051906020019080838360005b8381101561018e5780820151818401525b602001610175565b50505050905090810190601f1680156101bb5780820380516001836020036101000a031916815260200191505b509250505060405180910390a25b50505600a165627a7a723058209b5c2f4f3621447a23c841c03ca76d0a8e0ddba80b7c5d9d2be0ca9153d3e9e90029"
  val abi = "[{\"name\":\"emitEvent\",\"type\":\"function\",\"inputs\":[{\"name\":\"topic\",\"type\":\"string\",\"components\":null},{\"name\":\"value\",\"type\":\"string\",\"components\":null}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"Event\",\"inputs\":[{\"name\":\"topic\",\"type\":\"string\",\"indexed\":true,\"components\":null},{\"name\":\"value\",\"type\":\"string\",\"indexed\":false,\"components\":null}],\"anonymous\":false,\"type\":\"event\"}]"
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