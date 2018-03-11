package scalether.listener.transaction;

import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface MonoTransactionListener {
    boolean isEnabled();
    Mono<Void> onTransaction(String transactionHash, String blockHash, BigInteger blockNumber, int confirmations, boolean confirmed);
}
