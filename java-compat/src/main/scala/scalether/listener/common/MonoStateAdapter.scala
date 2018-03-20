package scalether.listener.common

import io.daonomic.blockchain.state.State
import reactor.core.publisher.Mono

class MonoStateAdapter[T](monoState: MonoState[T]) extends State[T, Mono] {
  override def get: Mono[Option[T]] =
    monoState.get()
      .map[Option[T]](t => Some(t))
      .switchIfEmpty(Mono.just(None))

  override def set(value: T): Mono[Unit] =
    monoState.set(value)
        .`then`(Mono.just())
}
