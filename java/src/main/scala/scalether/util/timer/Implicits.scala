package scalether.util.timer

object Implicits {
  implicit val sleeper = new JavaSleeper
  implicit val poller = new JavaPoller
}
