package org.toadallyarmed.system.level;

import org.toadallyarmed.util.Throttler;
import org.toadallyarmed.util.action.PayloadExtractor;

public class ThrottledLevelAction <T extends Record> implements LevelAction <T> {
    LevelAction<T> action;
    Throttler throttler;

    public ThrottledLevelAction(float tickRate, LevelAction<T> action) {
        this.action = action;
        this.throttler = new Throttler(tickRate);
    }

    @Override
    public boolean isDone() {
        return action.isDone();
    }

    @Override
    public void run(T payload) {
        throttler.runPeriodically(() -> action.run(payload));
    }

    @Override
    public PayloadExtractor<T, BasicLevelActionPayload> extractor() {
        return action.extractor();
    }
}
