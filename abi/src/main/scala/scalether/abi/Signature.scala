package scalether.abi

case class Signature[I, O](name: String, in: Type[I], out: Type[O]) {
  override def toString: String = name + in.string
}
