package daomao.contract

import cats.implicits._
import cats.{Functor, Monad}
import scalether.abi._
import scalether.abi.tuple._
import scalether.contract.Contract
import scalether.core.TransactionSender
import scalether.core.request.Transaction
import scalether.util.Hex
import scalether.util.transaction.TransactionService

import scala.language.higherKinds

class IssuedToken[F[_] : Functor](address: String, sender: TransactionSender[F]) extends Contract[F](address, sender) {
  def name: F[String] =
    call(Signature("name", UnitType, Tuple1Type(StringType)), ())

  def approve(_spender: String, _value: BigInt): F[Boolean] =
    call(Signature("approve", Tuple2Type(AddressType, Uint256Type), Tuple1Type(BoolType)), (_spender, _value))

  def totalSupply: F[BigInt] =
    call(Signature("totalSupply", UnitType, Tuple1Type(Uint256Type)), ())

  def callTransferFrom(_from: String, _to: String, _value: BigInt): F[Boolean] =
    call(Signature("transferFrom", Tuple3Type(AddressType, AddressType, Uint256Type), Tuple1Type(BoolType)), (_from, _to, _value))

  def transferFrom(_from: String, _to: String, _value: BigInt): F[String] =
    call(Signature("transferFrom", Tuple3Type(AddressType, AddressType, Uint256Type), Tuple1Type(BoolType)), (_from, _to, _value))

  def decimals: F[BigInt] =
    call(Signature("decimals", UnitType, Tuple1Type(Uint256Type)), ())

  def decreaseApproval(_spender: String, _subtractedValue: BigInt): F[Boolean] =
    call(Signature("decreaseApproval", Tuple2Type(AddressType, Uint256Type), Tuple1Type(BoolType)), (_spender, _subtractedValue))

  def balanceOf(_owner: String): F[BigInt] =
    call(Signature("balanceOf", Tuple1Type(AddressType), Tuple1Type(Uint256Type)), _owner)

  def owner: F[String] =
    call(Signature("owner", UnitType, Tuple1Type(AddressType)), ())

  def symbol: F[String] =
    call(Signature("symbol", UnitType, Tuple1Type(StringType)), ())

  def transfer(_to: String, _value: BigInt): F[Boolean] =
    call(Signature("transfer", Tuple2Type(AddressType, Uint256Type), Tuple1Type(BoolType)), (_to, _value))

  def increaseApproval(_spender: String, _addedValue: BigInt): F[Boolean] =
    call(Signature("increaseApproval", Tuple2Type(AddressType, Uint256Type), Tuple1Type(BoolType)), (_spender, _addedValue))

  def allowance(_owner: String, _spender: String): F[BigInt] =
    call(Signature("allowance", Tuple2Type(AddressType, AddressType), Tuple1Type(Uint256Type)), (_owner, _spender))

}

object IssuedToken {
  val bin = "0x6060604052341561000f57600080fd5b604051610b57380380610b5783398101604052808051820191906020018051820191906020018051919060200180519150505b6003848051610055929160200190610096565b506004838051610069929160200190610096565b5060008281556005829055600160a060020a03331681526001602052604090208290555b50505050610136565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106100d757805160ff1916838001178555610104565b82800160010185558215610104579182015b828111156101045782518255916020019190600101906100e9565b5b50610111929150610115565b5090565b61013391905b80821115610111576000815560010161011b565b5090565b90565b610a12806101456000396000f300606060405236156100b75763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166306fdde0381146100bc578063095ea7b31461014757806318160ddd1461017d57806323b872dd146101a2578063313ce567146101de578063661884631461020357806370a08231146102395780638da5cb5b1461026a57806395d89b4114610299578063a9059cbb14610324578063d73dd6231461035a578063dd62ed3e14610390575b600080fd5b34156100c757600080fd5b6100cf6103c7565b60405160208082528190810183818151815260200191508051906020019080838360005b8381101561010c5780820151818401525b6020016100f3565b50505050905090810190601f1680156101395780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561015257600080fd5b610169600160a060020a0360043516602435610465565b604051901515815260200160405180910390f35b341561018857600080fd5b61019061050c565b60405190815260200160405180910390f35b34156101ad57600080fd5b610169600160a060020a0360043581169060243516604435610512565b604051901515815260200160405180910390f35b34156101e957600080fd5b61019061063e565b60405190815260200160405180910390f35b341561020e57600080fd5b610169600160a060020a0360043516602435610644565b604051901515815260200160405180910390f35b341561024457600080fd5b610190600160a060020a0360043516610740565b60405190815260200160405180910390f35b341561027557600080fd5b61027d61075f565b604051600160a060020a03909116815260200160405180910390f35b34156102a457600080fd5b6100cf61076e565b60405160208082528190810183818151815260200191508051906020019080838360005b8381101561010c5780820151818401525b6020016100f3565b50505050905090810190601f1680156101395780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561032f57600080fd5b610169600160a060020a036004351660243561080c565b604051901515815260200160405180910390f35b341561036557600080fd5b610169600160a060020a03600435166024356108e3565b604051901515815260200160405180910390f35b341561039b57600080fd5b610190600160a060020a0360043581169060243516610988565b60405190815260200160405180910390f35b60038054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561045d5780601f106104325761010080835404028352916020019161045d565b820191906000526020600020905b81548152906001019060200180831161044057829003601f168201915b505050505081565b60008115806104975750600160a060020a03338116600090815260026020908152604080832093871683529290522054155b15156104a257600080fd5b600160a060020a03338116600081815260026020908152604080832094881680845294909152908190208590557f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b9259085905190815260200160405180910390a35060015b92915050565b60005481565b600080600160a060020a038416151561052a57600080fd5b50600160a060020a03808516600081815260026020908152604080832033909516835293815283822054928252600190529190912054610570908463ffffffff6109b516565b600160a060020a0380871660009081526001602052604080822093909355908616815220546105a5908463ffffffff6109cc16565b600160a060020a0385166000908152600160205260409020556105ce818463ffffffff6109b516565b600160a060020a03808716600081815260026020908152604080832033861684529091529081902093909355908616917fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9086905190815260200160405180910390a3600191505b509392505050565b60055481565b600160a060020a033381166000908152600260209081526040808320938616835292905290812054808311156106a157600160a060020a0333811660009081526002602090815260408083209388168352929052908120556106d8565b6106b1818463ffffffff6109b516565b600160a060020a033381166000908152600260209081526040808320938916835292905220555b600160a060020a0333811660008181526002602090815260408083209489168084529490915290819020547f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925915190815260200160405180910390a3600191505b5092915050565b600160a060020a0381166000908152600160205260409020545b919050565b600654600160a060020a031681565b60048054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561045d5780601f106104325761010080835404028352916020019161045d565b820191906000526020600020905b81548152906001019060200180831161044057829003601f168201915b505050505081565b6000600160a060020a038316151561082357600080fd5b600160a060020a03331660009081526001602052604090205461084c908363ffffffff6109b516565b600160a060020a033381166000908152600160205260408082209390935590851681522054610881908363ffffffff6109cc16565b600160a060020a0380851660008181526001602052604090819020939093559133909116907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9085905190815260200160405180910390a35060015b92915050565b600160a060020a03338116600090815260026020908152604080832093861683529290529081205461091b908363ffffffff6109cc16565b600160a060020a0333811660008181526002602090815260408083209489168084529490915290819020849055919290917f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92591905190815260200160405180910390a35060015b92915050565b600160a060020a038083166000908152600260209081526040808320938516835292905220545b92915050565b6000828211156109c157fe5b508082035b92915050565b6000828201838110156109db57fe5b8091505b50929150505600a165627a7a7230582089eaa1e535e04c31f46673ec24fefb8c8f39df9d288b3af20eb55710b5ac21d90029"
  val constructor = Tuple4Type(StringType, StringType, Uint256Type, Uint256Type)

  def encodeArgs(_name: String, _symbol: String, _totalSupply: BigInt, _decimals: BigInt): Array[Byte] =
    constructor.encode(_name, _symbol, _totalSupply, _decimals)

  def deployTransactionData(_name: String, _symbol: String, _totalSupply: BigInt, _decimals: BigInt): String =
    bin + Hex.bytesToHex(encodeArgs(_name, _symbol, _totalSupply, _decimals))

  def deploy[F[_] : Functor](sender: TransactionSender[F])(_name: String, _symbol: String, _totalSupply: BigInt, _decimals: BigInt): F[String] =
    sender.sendTransaction(Transaction(data = Some(deployTransactionData(_name, _symbol, _totalSupply, _decimals))))

  def deployAndWait[F[_] : Monad](sender: TransactionSender[F], service: TransactionService[F])
                                 (_name: String, _symbol: String, _totalSupply: BigInt, _decimals: BigInt): F[IssuedToken[F]] =
    deploy(sender)(_name, _symbol, _totalSupply, _decimals)
      .flatMap(hash => service.waitForTransaction(hash))
      .map(receipt => new IssuedToken[F](receipt.contractAddress, sender))

}
