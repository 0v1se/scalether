package scalether.listener.transaction;

import reactor.core.publisher.Mono;

public interface MonoTransactionListener {
    boolean isEnabled();
    Mono<Void> onTransaction(String txHash, int confirmations, boolean confirmed);
}
