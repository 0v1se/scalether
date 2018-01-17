package scalether.abi.json

import com.fasterxml.jackson.annotation.JsonProperty
import scalether.util.Hex

case class TruffleContract(@JsonProperty("contractName") name: String,
                           @JsonProperty("abi") abi: List[AbiItem],
                           @JsonProperty("bytecode") bin: String) {
  def isAbstract: Boolean = Hex.toBytes(bin).length == 0
}