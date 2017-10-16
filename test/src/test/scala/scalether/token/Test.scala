package scalether.token

import cats.implicits._
import scalether.domain.implicits._

object Test extends App with Rinkeby {
  val token = new ExternalToken("0xfb7c1523d817bdb467bdb8c1bdc13c6f993c9e84", sender)
  println(token.balanceOf("0x7c4890028c6f80d436de051148ed9014c1669427"))
}
