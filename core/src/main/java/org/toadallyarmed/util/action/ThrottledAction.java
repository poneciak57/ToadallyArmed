package org.toadallyarmed.util.action;

public class ThrottledAction<T extends Record, S extends Record> implements Action<T, S> {
    private final Action<T, S> action;
    private final float interval;
    private float accumulatedTime;
    private float lastTime = 0f;

    public ThrottledAction(float tickRate, Action<T, S> action) {
        this.interval = 1f / tickRate;
        this.action = action;
        this.accumulatedTime = interval;
    }

    @Override
    public void run(T payload) {
        final float currentNanoTime = System.nanoTime();
        float deltaTime = (currentNanoTime - lastTime) / 1_000_000_000f;
        this.lastTime = currentNanoTime;
        accumulatedTime += deltaTime;
        if (accumulatedTime >= interval) {
            action.run(payload);
            accumulatedTime = 0.f;
        }
    }

    @Override
    public PayloadExtractor<T, S> extractor() {
        return action.extractor();
    }
}
