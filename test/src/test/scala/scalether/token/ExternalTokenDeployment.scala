package scalether.token

import cats.implicits._
import scalether.domain.implicits._

object ExternalTokenDeployment extends App with Rinkeby {
  val token = ExternalToken.deployAndWait(sender, poller).get
  poller.waitForTransaction(token.mint("0x7c4890028c6f80d436de051148ed9014c1669427", 500, Array()))
}
