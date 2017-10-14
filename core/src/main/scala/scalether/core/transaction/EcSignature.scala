package scalether.core.transaction

case class EcSignature(v: Byte, r: Array[Byte], s: Array[Byte])
