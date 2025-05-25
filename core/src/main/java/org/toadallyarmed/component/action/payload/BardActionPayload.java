package org.toadallyarmed.component.action.payload;

import org.toadallyarmed.component.WalletComponent;
import org.toadallyarmed.util.action.PayloadExtractor;

import java.util.Optional;

public record BardActionPayload(
    WalletComponent walletComponent,
    int bardIncomeDelta
) {
    public static final PayloadExtractor<BardActionPayload, BasicActionPayload> EXTRACTOR = rawPayload -> Optional.of(new BardActionPayload(
        rawPayload.gameState().getWallet(),
        rawPayload.gameState().getGameConfig().moneyFrog().damage()
    ));
}
