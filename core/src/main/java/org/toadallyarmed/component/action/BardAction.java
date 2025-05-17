package org.toadallyarmed.component.action;

import org.toadallyarmed.component.action.payload.BardActionPayload;
import org.toadallyarmed.component.action.payload.BasicActionPayload;
import org.toadallyarmed.util.action.Action;
import org.toadallyarmed.util.action.PayloadExtractor;

public class BardAction implements Action<BardActionPayload, BasicActionPayload> {
    @Override
    public void run(BardActionPayload payload) {
        payload.walletComponent()
            .access()
            .addAndGet(payload.bardIncomeDelta());
    }

    @Override
    public PayloadExtractor<BardActionPayload, BasicActionPayload> extractor() {
        return BardActionPayload.EXTRACTOR;
    }
}
