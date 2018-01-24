package scalether.listener.transfer;

import reactor.core.publisher.Mono;

public interface MonoTransferListener {
    boolean isEnabled();
    Mono<Void> onTransfer(Transfer transfer, int confirmations, boolean confirmed);
}
