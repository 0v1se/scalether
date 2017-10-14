package scalether.extra.transaction

import org.web3j.crypto.Sign
import org.web3j.crypto.Sign.SignatureData
import org.web3j.rlp.{RlpEncoder, RlpList, RlpString, RlpType}
import scalether.core.request.Transaction
import scalether.domain.Word
import scalether.util.{Bytes, Hex}
import TransactionSigner._

class TransactionSigner(key: Word) {
  private val privateKey = key.toBigInteger
  private val publicKey = Sign.publicKeyFromPrivate(privateKey)

  def sign(transaction: Transaction): Array[Byte] = {
    val encodedTransaction = RlpEncoder.encode(new RlpList(asRlp(transaction):_*))
    val data = Sign.signMessage(encodedTransaction, publicKey, privateKey)
    RlpEncoder.encode(new RlpList(asRlp(transaction) ++ asRlp(data):_*))
  }
}

object TransactionSigner {
  def asRlp(transaction: Transaction): Array[RlpType] = Array(
    RlpString.create(transaction.nonce.get),
    RlpString.create(transaction.gasPrice.get),
    RlpString.create(transaction.gas.get),
    transaction.to.map(a => RlpString.create(a.bytes)).getOrElse(RlpString.create("")),
    RlpString.create(transaction.value),
    RlpString.create(Hex.toBytes(transaction.data))
  )

  protected def asRlp(ecSignature: SignatureData): Array[RlpType] = Array(
    RlpString.create(ecSignature.getV),
    RlpString.create(Bytes.trimLeadingZeroes(ecSignature.getR)),
    RlpString.create(Bytes.trimLeadingZeroes(ecSignature.getS)),
  )
}
