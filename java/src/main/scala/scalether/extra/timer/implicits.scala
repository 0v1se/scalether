package scalether.extra.timer

object implicits {
  implicit val sleeper = new JavaSleeper
  implicit val poller = new JavaPoller
}
