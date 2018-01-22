package scalether.extra.log;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import scalether.core.MonoEthereum;
import scalether.domain.response.Log;
import scalether.util.Hex;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class MonoLogListenerService {
    private final MonoEthereum ethereum;
    private final int confidence;
    private final MonoLogListener listener;
    private final MonoState<BigInteger> blockState;

    public MonoLogListenerService(MonoEthereum ethereum, int confidence, MonoLogListener listener, MonoState<BigInteger> blockState) {
        this.ethereum = ethereum;
        this.confidence = confidence;
        this.listener = listener;
        this.blockState = blockState;
    }

    public Mono<List<Log>> check() {
        Mono<Optional<BigInteger>> optionalBlock = blockState.get()
            .map(Optional::of)
            .switchIfEmpty(Mono.just(Optional.empty()));

        return Mono.zip(ethereum.ethBlockNumber(), optionalBlock).flatMap(tuple -> {
            Optional<BigInteger> savedBlock = tuple.getT2();
            BigInteger block = tuple.getT1();
            if (savedBlock.orElse(BigInteger.ZERO).equals(block)) {
                return Mono.just(Collections.emptyList());
            } else {
                final BigInteger fromBlock = savedBlock
                    .map(value -> value.subtract(BigInteger.valueOf(confidence)))
                    .orElse(BigInteger.ZERO);

                return listener.createFilter(Hex.prefixed(fromBlock), Hex.prefixed(block))
                    .flatMap(ethereum::ethGetJavaLogs)
                    .flatMap(logs -> notifyListenersAndSetState(block, logs));
            }
        });
    }

    private Mono<List<Log>> notifyListenersAndSetState(BigInteger block, List<Log> logs) {
        Stream<Mono<Void>> saves = logs.stream().map(log -> notifyListener(block, log));
        return Flux.fromStream(saves).flatMap(f -> f)
            .then(Mono.just(logs));
    }

    private Mono<Void> notifyListener(BigInteger block, Log log) {
        return listener.onLog(log, block.subtract(log.blockNumber()).compareTo(BigInteger.valueOf(confidence)) >= 0);
    }
}
