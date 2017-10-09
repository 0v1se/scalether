package scalether.abi.json

import com.fasterxml.jackson.annotation.JsonTypeInfo.As
import com.fasterxml.jackson.annotation.{JsonSubTypes, JsonTypeInfo}


@JsonSubTypes(Array(
  new JsonSubTypes.Type(value = classOf[AbiFunction], name = "function"),
  new JsonSubTypes.Type(value = classOf[AbiFunction], name = "constructor"),
  new JsonSubTypes.Type(value = classOf[AbiFunction], name = "default"),
  new JsonSubTypes.Type(value = classOf[AbiEvent], name = "event")
))
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = classOf[AbiFunction], visible = true, include = As.EXISTING_PROPERTY)
sealed trait AbiItem {

}

case class AbiFunction(name: String,
                       `type`: AbiFunctionType,
                       inputs: List[AbiComponent] = Nil,
                       outputs: List[AbiComponent] = Nil,
                       payable: Boolean = false,
                       constant: Boolean = false) extends AbiItem {
  def getType = Option(`type`).getOrElse(AbiFunctionType.FUNCTION)
}

case class AbiEvent(name: String,
                    inputs: List[AbiEventParam] = Nil,
                    anonymous: Boolean = false) extends AbiItem {
  val `type` = "event"

  def indexed = inputs.filter(_.indexed)

  def nonIndexed = inputs.filterNot(_.indexed)
}

case class AbiEventParam(name: String,
                         `type`: String,
                         indexed: Boolean = false,
                         components: List[AbiComponent] = Nil)

case class AbiComponent(name: String,
                        `type`: String,
                        components: List[AbiComponent] = Nil)
