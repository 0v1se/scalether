package scalether.listener.transfer;

import io.daonomic.blockchain.transfer.Transfer;
import reactor.core.publisher.Mono;

public interface MonoTransferListener {
    Mono<Void> onTransfer(Transfer transfer, int confirmations, boolean confirmed);
}
