package scalether.extra.log;

import reactor.core.publisher.Mono;

public interface MonoState<T> {
    Mono<T> get();
    Mono<Void> set(T value);
}
