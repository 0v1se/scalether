package scalether.listener.common;

import reactor.core.publisher.Mono;

public interface MonoState<T> {
    Mono<T> get();
    Mono<Void> set(T value);
}
