package org.toadallyarmed.component.action.payload;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.component.WalletComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.util.action.PayloadExtractor;

import java.util.Optional;

public record BardActionPayload(
    Vector2 pos,
    WalletComponent walletComponent,
    int bardIncomeDelta
) {
    public static final PayloadExtractor<BardActionPayload, BasicActionPayload> EXTRACTOR = rawPayload -> Optional.of(new BardActionPayload(
        rawPayload.entity().get(TransformComponent.class),
        rawPayload.gameState().getWallet(),
        rawPayload.gameState().getGameConfig().moneyFrog().damage()
    ));
}
