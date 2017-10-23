package scalether.extra.timer

object implicits {
  implicit val futureSleeper = new JavaSleeper
  implicit val futurePoller = new JavaPoller
  implicit val monoSleeper = new MonoSleeper
  implicit val monoPoller = new MonoPoller
}
