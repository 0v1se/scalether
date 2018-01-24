package scalether.listener.block;

import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface MonoBlockListener {
    boolean isEnabled();
    Mono<Void> onBlock(BigInteger blockNumber);
}
