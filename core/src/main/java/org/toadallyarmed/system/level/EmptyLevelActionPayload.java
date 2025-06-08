package org.toadallyarmed.system.level;

import org.toadallyarmed.util.action.PayloadExtractor;

import java.util.Optional;

public record EmptyLevelActionPayload() {
    public static final PayloadExtractor<EmptyLevelActionPayload, BasicLevelActionPayload> EXTRACTOR
        = rawPayload -> Optional.empty();
}
