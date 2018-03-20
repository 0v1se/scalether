package scalether.listener.block;

import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface MonoBlockListener {
    Mono<Void> onBlock(BigInteger blockNumber);
}
