package scalether.listener.transfer;

import reactor.core.publisher.Mono;

public class NoopMonoTransferListener implements MonoTransferListener {
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Mono<Void> onTransfer(Transfer transfer, int confirmations, boolean confirmed) {
        return Mono.empty();
    }
}
