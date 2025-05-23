package org.toadallyarmed.util.action;

import java.util.Optional;

public interface PayloadExtractor <T extends Record, S extends Record> {
    Optional<T> extract(S rawPayload);
}
