package scalether.listener.transfer;

import io.daonomic.blockchain.transfer.Transfer;
import reactor.core.publisher.Mono;

public class NoopMonoTransferListener implements MonoTransferListener {
    @Override
    public Mono<Void> onTransfer(Transfer transfer, int confirmations, boolean confirmed) {
        return Mono.empty();
    }
}
