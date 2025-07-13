package org.toadallyarmed.util.action;

import java.util.Optional;

public interface PayloadExtractor <Out, In> {
    Optional<Out> extract(In rawPayload);
}
