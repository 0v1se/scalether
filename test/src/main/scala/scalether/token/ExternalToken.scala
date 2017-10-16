package scalether.token

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

class ExternalToken[F[_]](address: Address, sender: TransactionSender[F])(implicit f: Functor[F])
  extends Contract[F](address, sender) {

  def callApprove(_spender: Address, _value: BigInteger): F[Boolean] =
    call(Signature("approve", Tuple2Type(AddressType, Uint256Type), Tuple1Type(BoolType)), (_spender, _value))

  def approve(_spender: Address, _value: BigInteger): F[String] =
    sendTransaction(Signature("approve", Tuple2Type(AddressType, Uint256Type), Tuple1Type(BoolType)), (_spender, _value))

  def totalSupply: F[BigInteger] =
    call(Signature("totalSupply", UnitType, Tuple1Type(Uint256Type)), ())

  def callTransferFrom(_from: Address, _to: Address, _value: BigInteger): F[Boolean] =
    call(Signature("transferFrom", Tuple3Type(AddressType, AddressType, Uint256Type), Tuple1Type(BoolType)), (_from, _to, _value))

  def transferFrom(_from: Address, _to: Address, _value: BigInteger): F[String] =
    sendTransaction(Signature("transferFrom", Tuple3Type(AddressType, AddressType, Uint256Type), Tuple1Type(BoolType)), (_from, _to, _value))

  def callTransferAndCall(_to: Address, _value: BigInteger, _data: Array[Byte]): F[Boolean] =
    call(Signature("transferAndCall", Tuple3Type(AddressType, Uint256Type, BytesType), Tuple1Type(BoolType)), (_to, _value, _data))

  def transferAndCall(_to: Address, _value: BigInteger, _data: Array[Byte]): F[String] =
    sendTransaction(Signature("transferAndCall", Tuple3Type(AddressType, Uint256Type, BytesType), Tuple1Type(BoolType)), (_to, _value, _data))

  def callMintAndCall(_to: Address, _value: BigInteger, _mintData: Array[Byte], _data: Array[Byte]): F[Boolean] =
    call(Signature("mintAndCall", Tuple4Type(AddressType, Uint256Type, BytesType, BytesType), Tuple1Type(BoolType)), (_to, _value, _mintData, _data))

  def mintAndCall(_to: Address, _value: BigInteger, _mintData: Array[Byte], _data: Array[Byte]): F[String] =
    sendTransaction(Signature("mintAndCall", Tuple4Type(AddressType, Uint256Type, BytesType, BytesType), Tuple1Type(BoolType)), (_to, _value, _mintData, _data))

  def callDecreaseApproval(_spender: Address, _subtractedValue: BigInteger): F[Boolean] =
    call(Signature("decreaseApproval", Tuple2Type(AddressType, Uint256Type), Tuple1Type(BoolType)), (_spender, _subtractedValue))

  def decreaseApproval(_spender: Address, _subtractedValue: BigInteger): F[String] =
    sendTransaction(Signature("decreaseApproval", Tuple2Type(AddressType, Uint256Type), Tuple1Type(BoolType)), (_spender, _subtractedValue))

  def balanceOf(_owner: Address): F[BigInteger] =
    call(Signature("balanceOf", Tuple1Type(AddressType), Tuple1Type(Uint256Type)), _owner)

  def owner: F[Address] =
    call(Signature("owner", UnitType, Tuple1Type(AddressType)), ())

  def callMint(_to: Address, _value: BigInteger, _mintData: Array[Byte]): F[Boolean] =
    call(Signature("mint", Tuple3Type(AddressType, Uint256Type, BytesType), Tuple1Type(BoolType)), (_to, _value, _mintData))

  def mint(_to: Address, _value: BigInteger, _mintData: Array[Byte]): F[String] =
    sendTransaction(Signature("mint", Tuple3Type(AddressType, Uint256Type, BytesType), Tuple1Type(BoolType)), (_to, _value, _mintData))

  def callTransfer(_to: Address, _value: BigInteger): F[Boolean] =
    call(Signature("transfer", Tuple2Type(AddressType, Uint256Type), Tuple1Type(BoolType)), (_to, _value))

  def transfer(_to: Address, _value: BigInteger): F[String] =
    sendTransaction(Signature("transfer", Tuple2Type(AddressType, Uint256Type), Tuple1Type(BoolType)), (_to, _value))

  def callTransfer(_to: Address, _value: BigInteger, _data: Array[Byte]): F[Boolean] =
    call(Signature("transfer", Tuple3Type(AddressType, Uint256Type, BytesType), Tuple1Type(BoolType)), (_to, _value, _data))

  def transfer(_to: Address, _value: BigInteger, _data: Array[Byte]): F[String] =
    sendTransaction(Signature("transfer", Tuple3Type(AddressType, Uint256Type, BytesType), Tuple1Type(BoolType)), (_to, _value, _data))

  def callIncreaseApproval(_spender: Address, _addedValue: BigInteger): F[Boolean] =
    call(Signature("increaseApproval", Tuple2Type(AddressType, Uint256Type), Tuple1Type(BoolType)), (_spender, _addedValue))

  def increaseApproval(_spender: Address, _addedValue: BigInteger): F[String] =
    sendTransaction(Signature("increaseApproval", Tuple2Type(AddressType, Uint256Type), Tuple1Type(BoolType)), (_spender, _addedValue))

  def allowance(_owner: Address, _spender: Address): F[BigInteger] =
    call(Signature("allowance", Tuple2Type(AddressType, AddressType), Tuple1Type(Uint256Type)), (_owner, _spender))

  def callTransferOwnership(newOwner: Address): F[Unit] =
    call(Signature("transferOwnership", Tuple1Type(AddressType), UnitType), newOwner)

  def transferOwnership(newOwner: Address): F[String] =
    sendTransaction(Signature("transferOwnership", Tuple1Type(AddressType), UnitType), newOwner)

  def callBurn(_value: BigInteger, _data: Array[Byte]): F[Unit] =
    call(Signature("burn", Tuple2Type(Uint256Type, BytesType), UnitType), (_value, _data))

  def burn(_value: BigInteger, _data: Array[Byte]): F[String] =
    sendTransaction(Signature("burn", Tuple2Type(Uint256Type, BytesType), UnitType), (_value, _data))

}

object ExternalToken extends ContractObject {
  val name = "ExternalToken"
  val abi = "[{\"name\":\"approve\",\"type\":\"function\",\"inputs\":[{\"name\":\"_spender\",\"type\":\"address\",\"components\":null},{\"name\":\"_value\",\"type\":\"uint256\",\"components\":null}],\"outputs\":[{\"name\":\"\",\"type\":\"bool\",\"components\":null}],\"payable\":false,\"constant\":false},{\"name\":\"totalSupply\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"uint256\",\"components\":null}],\"payable\":false,\"constant\":true},{\"name\":\"transferFrom\",\"type\":\"function\",\"inputs\":[{\"name\":\"_from\",\"type\":\"address\",\"components\":null},{\"name\":\"_to\",\"type\":\"address\",\"components\":null},{\"name\":\"_value\",\"type\":\"uint256\",\"components\":null}],\"outputs\":[{\"name\":\"\",\"type\":\"bool\",\"components\":null}],\"payable\":false,\"constant\":false},{\"name\":\"transferAndCall\",\"type\":\"function\",\"inputs\":[{\"name\":\"_to\",\"type\":\"address\",\"components\":null},{\"name\":\"_value\",\"type\":\"uint256\",\"components\":null},{\"name\":\"_data\",\"type\":\"bytes\",\"components\":null}],\"outputs\":[{\"name\":\"\",\"type\":\"bool\",\"components\":null}],\"payable\":false,\"constant\":false},{\"name\":\"mintAndCall\",\"type\":\"function\",\"inputs\":[{\"name\":\"_to\",\"type\":\"address\",\"components\":null},{\"name\":\"_value\",\"type\":\"uint256\",\"components\":null},{\"name\":\"_mintData\",\"type\":\"bytes\",\"components\":null},{\"name\":\"_data\",\"type\":\"bytes\",\"components\":null}],\"outputs\":[{\"name\":\"\",\"type\":\"bool\",\"components\":null}],\"payable\":false,\"constant\":false},{\"name\":\"decreaseApproval\",\"type\":\"function\",\"inputs\":[{\"name\":\"_spender\",\"type\":\"address\",\"components\":null},{\"name\":\"_subtractedValue\",\"type\":\"uint256\",\"components\":null}],\"outputs\":[{\"name\":\"success\",\"type\":\"bool\",\"components\":null}],\"payable\":false,\"constant\":false},{\"name\":\"balanceOf\",\"type\":\"function\",\"inputs\":[{\"name\":\"_owner\",\"type\":\"address\",\"components\":null}],\"outputs\":[{\"name\":\"balance\",\"type\":\"uint256\",\"components\":null}],\"payable\":false,\"constant\":true},{\"name\":\"owner\",\"type\":\"function\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"address\",\"components\":null}],\"payable\":false,\"constant\":true},{\"name\":\"mint\",\"type\":\"function\",\"inputs\":[{\"name\":\"_to\",\"type\":\"address\",\"components\":null},{\"name\":\"_value\",\"type\":\"uint256\",\"components\":null},{\"name\":\"_mintData\",\"type\":\"bytes\",\"components\":null}],\"outputs\":[{\"name\":\"\",\"type\":\"bool\",\"components\":null}],\"payable\":false,\"constant\":false},{\"name\":\"transfer\",\"type\":\"function\",\"inputs\":[{\"name\":\"_to\",\"type\":\"address\",\"components\":null},{\"name\":\"_value\",\"type\":\"uint256\",\"components\":null}],\"outputs\":[{\"name\":\"\",\"type\":\"bool\",\"components\":null}],\"payable\":false,\"constant\":false},{\"name\":\"transfer\",\"type\":\"function\",\"inputs\":[{\"name\":\"_to\",\"type\":\"address\",\"components\":null},{\"name\":\"_value\",\"type\":\"uint256\",\"components\":null},{\"name\":\"_data\",\"type\":\"bytes\",\"components\":null}],\"outputs\":[{\"name\":\"\",\"type\":\"bool\",\"components\":null}],\"payable\":false,\"constant\":false},{\"name\":\"increaseApproval\",\"type\":\"function\",\"inputs\":[{\"name\":\"_spender\",\"type\":\"address\",\"components\":null},{\"name\":\"_addedValue\",\"type\":\"uint256\",\"components\":null}],\"outputs\":[{\"name\":\"success\",\"type\":\"bool\",\"components\":null}],\"payable\":false,\"constant\":false},{\"name\":\"allowance\",\"type\":\"function\",\"inputs\":[{\"name\":\"_owner\",\"type\":\"address\",\"components\":null},{\"name\":\"_spender\",\"type\":\"address\",\"components\":null}],\"outputs\":[{\"name\":\"remaining\",\"type\":\"uint256\",\"components\":null}],\"payable\":false,\"constant\":true},{\"name\":\"transferOwnership\",\"type\":\"function\",\"inputs\":[{\"name\":\"newOwner\",\"type\":\"address\",\"components\":null}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"burn\",\"type\":\"function\",\"inputs\":[{\"name\":\"_value\",\"type\":\"uint256\",\"components\":null},{\"name\":\"_data\",\"type\":\"bytes\",\"components\":null}],\"outputs\":[],\"payable\":false,\"constant\":false},{\"name\":\"Mint\",\"inputs\":[{\"name\":\"to\",\"type\":\"address\",\"indexed\":true,\"components\":null},{\"name\":\"value\",\"type\":\"uint256\",\"indexed\":false,\"components\":null}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"Burn\",\"inputs\":[{\"name\":\"burner\",\"type\":\"address\",\"indexed\":true,\"components\":null},{\"name\":\"value\",\"type\":\"uint256\",\"indexed\":false,\"components\":null},{\"name\":\"data\",\"type\":\"bytes\",\"indexed\":false,\"components\":null}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"OwnershipTransferred\",\"inputs\":[{\"name\":\"previousOwner\",\"type\":\"address\",\"indexed\":true,\"components\":null},{\"name\":\"newOwner\",\"type\":\"address\",\"indexed\":true,\"components\":null}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"Transfer\",\"inputs\":[{\"name\":\"from\",\"type\":\"address\",\"indexed\":true,\"components\":null},{\"name\":\"to\",\"type\":\"address\",\"indexed\":true,\"components\":null},{\"name\":\"value\",\"type\":\"uint256\",\"indexed\":false,\"components\":null},{\"name\":\"data\",\"type\":\"bytes\",\"indexed\":false,\"components\":null}],\"anonymous\":false,\"type\":\"event\"},{\"name\":\"Approval\",\"inputs\":[{\"name\":\"owner\",\"type\":\"address\",\"indexed\":true,\"components\":null},{\"name\":\"spender\",\"type\":\"address\",\"indexed\":true,\"components\":null},{\"name\":\"value\",\"type\":\"uint256\",\"indexed\":false,\"components\":null}],\"anonymous\":false,\"type\":\"event\"}]"
  val bin = "0x60606040525b60038054600160a060020a03191633600160a060020a03161790555b5b6111f9806100316000396000f300606060405236156100bf5763ffffffff60e060020a600035041663095ea7b381146100c457806318160ddd146100fa57806323b872dd1461011f5780634000aea01461015b5780635ad8803d146101d4578063661884631461028f57806370a08231146102c55780638da5cb5b146102f657806394d008ef14610325578063a9059cbb1461039e578063be45fd62146103d4578063d73dd6231461044d578063dd62ed3e14610483578063f2fde38b146104ba578063fe9d9303146104db575b600080fd5b34156100cf57600080fd5b6100e6600160a060020a0360043516602435610533565b604051901515815260200160405180910390f35b341561010557600080fd5b61010d6105a0565b60405190815260200160405180910390f35b341561012a57600080fd5b6100e6600160a060020a03600435811690602435166044356105a6565b604051901515815260200160405180910390f35b341561016657600080fd5b6100e660048035600160a060020a03169060248035919060649060443590810190830135806020601f820181900481020160405190810160405281815292919060208401838380828437509496506106f895505050505050565b604051901515815260200160405180910390f35b34156101df57600080fd5b6100e660048035600160a060020a03169060248035919060649060443590810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405281815292919060208401838380828437509496506108dc95505050505050565b604051901515815260200160405180910390f35b341561029a57600080fd5b6100e6600160a060020a0360043516602435610a2b565b604051901515815260200160405180910390f35b34156102d057600080fd5b61010d600160a060020a0360043516610b27565b60405190815260200160405180910390f35b341561030157600080fd5b610309610b46565b604051600160a060020a03909116815260200160405180910390f35b341561033057600080fd5b6100e660048035600160a060020a03169060248035919060649060443590810190830135806020601f82018190048102016040519081016040528181529291906020840183838082843750949650610b5595505050505050565b604051901515815260200160405180910390f35b34156103a957600080fd5b6100e6600160a060020a0360043516602435610ba5565b604051901515815260200160405180910390f35b34156103df57600080fd5b6100e660048035600160a060020a03169060248035919060649060443590810190830135806020601f82018190048102016040519081016040528181529291906020840183838082843750949650610c6d95505050505050565b604051901515815260200160405180910390f35b341561045857600080fd5b6100e6600160a060020a0360043516602435610c84565b604051901515815260200160405180910390f35b341561048e57600080fd5b61010d600160a060020a0360043581169060243516610d29565b60405190815260200160405180910390f35b34156104c557600080fd5b6104d9600160a060020a0360043516610d56565b005b34156104e657600080fd5b6104d9600480359060446024803590810190830135806020601f82018190048102016040519081016040528181529291906020840183838082843750949650610def95505050505050565b005b600160a060020a03338116600081815260026020908152604080832094871680845294909152808220859055909291907f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b9259085905190815260200160405180910390a35060015b92915050565b60005481565b6000600160a060020a03831615156105bd57600080fd5b600160a060020a0384166000908152600160205260409020548211156105e257600080fd5b600160a060020a038085166000908152600260209081526040808320339094168352929052205482111561061557600080fd5b600160a060020a03841660009081526001602052604090205461063e908363ffffffff610f3216565b600160a060020a038086166000908152600160205260408082209390935590851681522054610673908363ffffffff610f4916565b600160a060020a038085166000908152600160209081526040808320949094558783168252600281528382203390931682529190915220546106bb908363ffffffff610f3216565b600160a060020a03808616600090815260026020908152604080832033909416835292905220556106ed848484610f63565b5060015b9392505050565b6000600160a060020a038416151561070f57600080fd5b600160a060020a03331660009081526001602052604090205483111561073457600080fd5b600160a060020a03331660009081526001602052604090205461075d908463ffffffff610f3216565b600160a060020a033381166000908152600160205260408082209390935590861681522054610792908463ffffffff610f4916565b600160a060020a0385166000908152600160205260409020556107b733858585610f83565b83600160a060020a03166040517f6f6e546f6b656e5472616e7366657228616464726573732c75696e743235362c81527f62797465732900000000000000000000000000000000000000000000000000006020820152602601604051809103902060e060020a90043385856040518463ffffffff1660e060020a0281526004018084600160a060020a0316600160a060020a03168152602001838152602001828051906020019080838360005b8381101561087d5780820151818401525b602001610864565b50505050905090810190601f1680156108aa5780820380516001836020036101000a031916815260200191505b50935050505060006040518083038160008761646e5a03f19250505015156106ed57600080fd5b5060015b9392505050565b60035460009033600160a060020a039081169116146108fa57600080fd5b610905858585611086565b6109126000868685610f83565b84600160a060020a03166040517f6f6e546f6b656e5472616e7366657228616464726573732c75696e743235362c81527f62797465732900000000000000000000000000000000000000000000000000006020820152602601604051809103902060e060020a9004600086856040518463ffffffff1660e060020a028152600401808460ff168152602001838152602001828051906020019080838360005b838110156109ca5780820151818401525b6020016109b1565b50505050905090810190601f1680156109f75780820380516001836020036101000a031916815260200191505b50935050505060006040518083038160008761646e5a03f1925050501515610a1e57600080fd5b5060015b5b949350505050565b600160a060020a03338116600090815260026020908152604080832093861683529290529081205480831115610a8857600160a060020a033381166000908152600260209081526040808320938816835292905290812055610abf565b610a98818463ffffffff610f3216565b600160a060020a033381166000908152600260209081526040808320938916835292905220555b600160a060020a0333811660008181526002602090815260408083209489168084529490915290819020547f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925915190815260200160405180910390a3600191505b5092915050565b600160a060020a0381166000908152600160205260409020545b919050565b600354600160a060020a031681565b60035460009033600160a060020a03908116911614610b7357600080fd5b610b7e848484611086565b6106ed60008585602060405190810160405260008152610f83565b5060015b5b9392505050565b6000600160a060020a0383161515610bbc57600080fd5b600160a060020a033316600090815260016020526040902054821115610be157600080fd5b600160a060020a033316600090815260016020526040902054610c0a908363ffffffff610f3216565b600160a060020a033381166000908152600160205260408082209390935590851681522054610c3f908363ffffffff610f4916565b600160a060020a038416600090815260016020526040902055610c63338484610f63565b5060015b92915050565b6000610c7a8484846106f8565b90505b9392505050565b600160a060020a033381166000908152600260209081526040808320938616835292905290812054610cbc908363ffffffff610f4916565b600160a060020a0333811660008181526002602090815260408083209489168084529490915290819020849055919290917f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92591905190815260200160405180910390a35060015b92915050565b600160a060020a038083166000908152600260209081526040808320938516835292905220545b92915050565b60035433600160a060020a03908116911614610d7157600080fd5b600160a060020a0381161515610d8657600080fd5b600354600160a060020a0380831691167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a36003805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0383161790555b5b50565b6000808311610dfd57600080fd5b600160a060020a033316600090815260016020526040902054831115610e2257600080fd5b610e2c83836111c8565b5033600160a060020a038116600090815260016020526040902054610e519084610f32565b600160a060020a03821660009081526001602052604081209190915554610e7e908463ffffffff610f3216565b600055600160a060020a0381167f8d38f5a0c1764ff1cca876ce8fe136163fddfce925659e6ad05437cfff6fd392848460405182815260406020820181815290820183818151815260200191508051906020019080838360005b83811015610ef15780820151818401525b602001610ed8565b50505050905090810190601f168015610f1e5780820380516001836020036101000a031916815260200191505b50935050505060405180910390a25b505050565b600082821115610f3e57fe5b508082035b92915050565b600082820183811015610f5857fe5b8091505b5092915050565b610f2d838383602060405190810160405260008152610f83565b5b505050565b82600160a060020a031684600160a060020a03167fe19260aff97b920c7df27010903aeb9c8d2be5d310a2c67824cf3f15396e4c16848460405182815260406020820181815290820183818151815260200191508051906020019080838360005b83811015610ffd5780820151818401525b602001610fe4565b50505050905090810190601f16801561102a5780820380516001836020036101000a031916815260200191505b50935050505060405180910390a382600160a060020a031684600160a060020a03167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef8460405190815260200160405180910390a35b50505050565b600054611099908363ffffffff610f4916565b6000908155600160a060020a0384168152600160205260409020546110c4908363ffffffff610f4916565b600160a060020a0384166000818152600160205260409081902092909255907f13b4590e2f417016fce3f02298116b2ad6220e5ee149b4c55d2f1d9f5012762390849084905182815260406020820181815290820183818151815260200191508051906020019080838360005b8381101561114a5780820151818401525b602001611131565b50505050905090810190601f1680156111775780820380516001836020036101000a031916815260200191505b50935050505060405180910390a282600160a060020a03167f0f6798a560793a54c3bcfe86a93cde1e73087d944c0ea20544137d41213968858360405190815260200160405180910390a25b505050565b5b50505600a165627a7a72305820709d475c3bfc7953fbf51e4e0968d8a05897882956a750003745d6eedd1c41230029"

  val constructor = UnitType

  def encodeArgs: Array[Byte] =
    constructor.encode()

  def deployTransactionData: String =
    bin + Hex.to(encodeArgs)

  def deploy[F[_]](sender: TransactionSender[F])(implicit f: Functor[F]): F[String] =
    sender.sendTransaction(Transaction(data = deployTransactionData))

  def deployAndWait[F[_]](sender: TransactionSender[F], poller: TransactionPoller[F])(implicit m: Monad[F]) : F[ExternalToken[F]] =
      poller.waitForTransaction(deploy(sender))
      .map(receipt => new ExternalToken[F](receipt.contractAddress, sender))
}

case class Mint(to: Address, value: BigInteger)

object Mint {
  val event = Event("Mint", List(AddressType, Uint256Type), Tuple1Type(AddressType), Tuple1Type(Uint256Type))

  def apply(log: Log): Mint = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val to = event.indexed.type1.decode(Hex.toBytes(log.topics(1)), 0).value
    val value = decodedData
    Mint(to, value)
  }
}

case class Burn(burner: Address, value: BigInteger, data: Array[Byte])

object Burn {
  val event = Event("Burn", List(AddressType, Uint256Type, BytesType), Tuple1Type(AddressType), Tuple2Type(Uint256Type, BytesType))

  def apply(log: Log): Burn = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val burner = event.indexed.type1.decode(Hex.toBytes(log.topics(1)), 0).value
    val value = decodedData._1
    val data = decodedData._2
    Burn(burner, value, data)
  }
}

case class OwnershipTransferred(previousOwner: Address, newOwner: Address)

object OwnershipTransferred {
  val event = Event("OwnershipTransferred", List(AddressType, AddressType), Tuple2Type(AddressType, AddressType), UnitType)

  def apply(log: Log): OwnershipTransferred = {
    assert(log.topics.head == event.id)

    
    val previousOwner = event.indexed.type1.decode(Hex.toBytes(log.topics(1)), 0).value
    val newOwner = event.indexed.type2.decode(Hex.toBytes(log.topics(2)), 0).value
    OwnershipTransferred(previousOwner, newOwner)
  }
}

case class Transfer(from: Address, to: Address, value: BigInteger, data: Array[Byte])

object Transfer {
  val event = Event("Transfer", List(AddressType, AddressType, Uint256Type, BytesType), Tuple2Type(AddressType, AddressType), Tuple2Type(Uint256Type, BytesType))

  def apply(log: Log): Transfer = {
    assert(log.topics.head == event.id)

    val decodedData = event.decode(log.data)
    val from = event.indexed.type1.decode(Hex.toBytes(log.topics(1)), 0).value
    val to = event.indexed.type2.decode(Hex.toBytes(log.topics(2)), 0).value
    val value = decodedData._1
    val data = decodedData._2
    Transfer(from, to, value, data)
  }
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

