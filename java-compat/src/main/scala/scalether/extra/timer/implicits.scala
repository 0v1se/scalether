package scalether.extra.timer

object implicits {
  implicit val monoSleeper = new MonoSleeper
  implicit val monoPoller = new MonoPoller
}
