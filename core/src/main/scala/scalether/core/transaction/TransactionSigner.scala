package scalether.core.transaction

import org.web3j.rlp.RlpString
import scalether.core.request.Transaction
import scalether.domain.Keys
import scalether.util.{Bytes, Hex}

class TransactionSigner(keys: Keys) {
  def sign(transaction: Transaction): Array[Byte] = {
    null
  }

  private def encode(transaction: Transaction, ecSignature: Option[EcSignature]) = {

  }

}

object TransactionSigner {
  def asRlp(transaction: Transaction) = List(
    RlpString.create(transaction.nonce.get),
    RlpString.create(transaction.gasPrice.get),
    RlpString.create(transaction.gas.get),
    transaction.to.map(a => RlpString.create(a.bytes)).getOrElse(RlpString.create("")),
    RlpString.create(transaction.value),
    RlpString.create(Hex.toBytes(transaction.data))
  )

  protected def asRlp(ecSignature: EcSignature) = List(
    RlpString.create(ecSignature.v),
    RlpString.create(Bytes.trimLeadingZeroes(ecSignature.r)),
    RlpString.create(Bytes.trimLeadingZeroes(ecSignature.s)),
  )
}
