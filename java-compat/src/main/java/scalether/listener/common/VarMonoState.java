package scalether.listener.common;

import reactor.core.publisher.Mono;

public class VarMonoState<T> implements MonoState<T> {
    private volatile T value;

    public VarMonoState(T value) {
        this.value = value;
    }

    public VarMonoState() {
    }

    @Override
    public Mono<T> get() {
        return Mono.justOrEmpty(value);
    }

    @Override
    public Mono<Void> set(T value) {
        this.value = value;
        return Mono.empty();
    }
}
