package org.toadallyarmed.util.action;

import org.toadallyarmed.util.Throttler;

public class ThrottledAction<T extends Record, S extends Record> implements Action<T, S> {
    private final Action<T, S> action;
    Throttler throttler;

    public ThrottledAction(float tickRate, Action<T, S> action) {
        this.throttler = new Throttler(tickRate);
        this.action = action;
    }

    @Override
    public void run(T payload) {
        throttler.runPeriodically(() -> action.run(payload));
    }

    @Override
    public PayloadExtractor<T, S> extractor() {
        return action.extractor();
    }
}
