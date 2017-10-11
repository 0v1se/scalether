<#macro monad><#compress>
    <#if F?has_content>
        ${F}
    <#else>
        F
    </#if>
</#compress></#macro>
<#macro monad_param><#compress>
    <#if !(F?has_content)>
        [F[_]]
    </#if>
</#compress></#macro>
<#macro sender><#compress>
    <#if transactionSender?has_content>
        ${transactionSender}
    <#else>
        TransactionSender[<@monad/>]
    </#if>
</#compress></#macro>
<#macro service><#compress>
    <#if transactionService?has_content>
        ${transactionService}
    <#else>
        TransactionService[<@monad/>]
    </#if>
</#compress></#macro>
<#macro implicit><#compress>
    <#if !(monadImport?has_content)>
        <#nested/>
    </#if>
</#compress></#macro>
<#macro single_scala_type abiType><#compress>
    <#if abiType == 'address'>
        Address
    <#elseif abiType == 'string'>
        String
    <#elseif abiType?starts_with("uint")>
        BigInteger
    <#elseif abiType == "bool">
        Boolean
    <#elseif abiType?starts_with("bytes")>
        Array[Byte]
    <#else>
        generic
    </#if>
</#compress></#macro>
<#macro scala_type types><#compress>
    <#if types?size == 0>
        Unit
    <#else>
        <#list types as type><@single_scala_type type.type/><#if type?has_next>, </#if></#list>
    </#if>
</#compress></#macro>
<#macro single_type abiType><#compress>
    <#if abiType == 'address'>
        AddressType
    <#elseif abiType == 'string'>
        StringType
    <#elseif abiType == 'uint'>
        Uint${abiType?substring(4)}Type
    <#elseif abiType?starts_with("uint")>
        Uint${abiType?substring(4)}Type
    <#elseif abiType == "bool">
        BoolType
    <#elseif abiType == "bytes">
        BytesType
    <#elseif abiType?starts_with("bytes")>
        Bytes${abiType?substring(5)}Type
    <#else>
        Type
    </#if>
</#compress></#macro>
<#macro type_list types=[]><#compress>
    <#list types as type><@single_type type.type/><#if type?has_next>, </#if></#list>
</#compress></#macro>
<#macro type types=[]><#compress>
    <#if types?size == 0>
        UnitType
    <#else>
        Tuple${types?size}Type(<@type_list types/>)
    </#if>
</#compress></#macro>
<#macro signature item>Signature("${item.name}", <@type item.inputs/>, <@type item.outputs/>)</#macro>
<#macro output_type types=[]><#compress>
    <#if types?size == 1 || types?size == 0>
        <@scala_type types/>
    <#else>
        (<@scala_type types/>)
    </#if>
</#compress></#macro>
<#function isHashTopic arg>
    <#return arg.type == 'string'>
</#function>
<#macro event_arg_type arg><#compress>
    <#if arg.indexed && isHashTopic(arg)>
        Hash
    <#else>
        <@single_scala_type arg.type/>
    </#if>
</#compress></#macro>
<#macro event_indexed_arg arg index><#compress>
    <#if isHashTopic(arg)>
        Hash(log.topics(${index + 1}))
    <#else>
        event.indexed.type${index + 1}.decode(Hex.toBytes(log.topics(${index + 1})), 0).value
    </#if>
</#compress></#macro>
<#macro event_non_indexed_arg arg index><#compress>
    decodedData._${index + 1}
</#compress></#macro>
<#macro args inputs><#if inputs?has_content>(<#list inputs as inp>${inp.name}: <@single_scala_type inp.type/><#if inp?has_next>, </#if></#list>)</#if></#macro>
<#macro args_values inputs><#list inputs as inp>${inp.name}<#if inp?has_next>, </#if></#list></#macro>
<#macro args_params inputs><#if inputs?size != 0>(</#if><@args_values inputs/><#if inputs?size != 0>)</#if></#macro>
<#macro args_tuple inputs><#if inputs?size != 1>(</#if><@args_values inputs/><#if inputs?size != 1>)</#if></#macro>
<#function find_constructor_args>
    <#list truffle.abi as item>
        <#if item.type != "event" && item.type?? && item.type.getId == 'constructor'>
            <#return item.inputs/>
        </#if>
    </#list>
    <#return []/>
</#function>
<#assign constructor_args = find_constructor_args()/>
package ${package}

import java.math.BigInteger

<#if monadType?has_content>
import ${monadType}
</#if>
<#if monadImport?has_content>
import ${monadImport}
</#if>
import cats.implicits._
import cats.{Functor, Monad}
import scalether.abi._
import scalether.abi.data._
import scalether.abi.tuple._
import scalether.contract._
import scalether.core._
import scalether.core.request.Transaction
import scalether.util.Hex
import scalether.util.transaction.TransactionService

import scala.language.higherKinds

class ${truffle.name}<@monad_param/>(address: String, sender: <@sender/>)<@implicit>(implicit f: Functor[<@monad/>])</@>
  extends Contract[<@monad/>](address, sender) {

  <#list truffle.abi as item>
        <#if item.type != 'event' && item.name??>
            <#if item.constant>
  def ${item.name}<@args item.inputs/>: <@monad/>[<@output_type item.outputs/>] =
    call(<@signature item/>, <@args_tuple item.inputs/>)
            <#else>
  def call${item.name?cap_first}<@args item.inputs/>: <@monad/>[<@output_type item.outputs/>] =
    call(<@signature item/>, <@args_tuple item.inputs/>)

  def ${item.name}<@args item.inputs/>: <@monad/>[String] =
    sendTransaction(<@signature item/>, <@args_tuple item.inputs/>)
            </#if>

        </#if>
    </#list>
}

object ${truffle.name} extends ContractObject {
  val name = "${truffle.name}"
  val bin = "${truffle.bin}"
  val abi = ${abi}
  val constructor = <@type constructor_args/>

  def encodeArgs<@args constructor_args/>: Array[Byte] =
    constructor.encode(<@args_values constructor_args/>)

  def deployTransactionData<@args constructor_args/>: String =
    bin + Hex.toHex(encodeArgs<@args_params constructor_args/>)

  def deploy<@monad_param/>(sender: <@sender/>)<@implicit>(implicit f: Functor[<@monad/>])</@><@args constructor_args/>: <@monad/>[String] =
    sender.sendTransaction(Transaction(data = Some(deployTransactionData<@args_params constructor_args/>)))

  def deployAndWait<@monad_param/>(sender: <@sender/>, service: <@service/>)<@implicit>(implicit m: Monad[<@monad/>])</@> <@args constructor_args/>: <@monad/>[${truffle.name}<#if !(F?has_content)>[F]</#if>] =
    deploy(sender)<@args_params constructor_args/>
      .flatMap(hash => service.waitForTransaction(hash))
      .map(receipt => new ${truffle.name}<#if !(F?has_content)>[F]</#if>(receipt.contractAddress, sender))

  <#list truffle.abi as item>
      <#if item.type == "event">
  case class ${item.name}(<#list item.all as arg>${arg.name}: <@event_arg_type arg/><#if arg?has_next>, </#if></#list>)

  object ${item.name} {
    val event = Event("${item.name}", List(<@type_list item.inputs/>), <@type item.indexed/>, <@type item.nonIndexed/>)

    def apply(log: Log): ${item.name} = {
      assert(log.topics.head == event.id)

      <#if item.nonIndexed?has_content>val decodedData = event.decode(log.data)</#if>
      <#list item.indexed as arg>
      val ${arg.name} = <@event_indexed_arg arg arg?index/>
      </#list>
      <#if item.nonIndexed?size == 1>
          <#list item.nonIndexed as arg>
      val ${arg.name} = decodedData
          </#list>
      <#else>
          <#list item.nonIndexed as arg>
      val ${arg.name} = <@event_non_indexed_arg arg arg?index/>
          </#list>
      </#if>
      ${item.name}(<#list item.all as arg>${arg.name}<#if arg?has_next>, </#if></#list>)
    }
  }

      </#if>
  </#list>
}