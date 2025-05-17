package org.toadallyarmed.util.action;

import java.util.Optional;

public interface Action <T extends Record, S extends Record> {
    void run(T payload);
    PayloadExtractor<T, S> extractor();
    default void extract_run(S rawPayload) {
        Optional<T> extracted = extractor().extract(rawPayload);
        extracted.ifPresent(this::run);
    }
}
