package scalether.log

import reactor.core.publisher.Mono
import scalether.extra.log.MonoState

class MonoStateAdapter[T](monoState: MonoState[T]) extends State[T, Mono] {
  override def get: Mono[Option[T]] =
    monoState.get().map[Option[T]](t => Some(t)).switchIfEmpty(Mono.just(None))

  override def set(value: T): Mono[Unit] =
    monoState.set(value)
      .map[Unit](_ => ())
      .switchIfEmpty(Mono.just())
}
