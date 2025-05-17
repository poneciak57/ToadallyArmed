package org.toadallyarmed.component;

import org.toadallyarmed.component.action.payload.BasicActionPayload;
import org.toadallyarmed.component.interfaces.ActionComponent;
import org.toadallyarmed.util.action.Action;

public class ThrottledActionComponent implements ActionComponent {
    private final Action<?, BasicActionPayload> action;
    private final float interval;
    private float accumulatedTime = 0.0f;

    public ThrottledActionComponent(float tickRate, Action<?, BasicActionPayload> action) {
        this.interval = 1f / tickRate;
        this.action = action;
    }
    @Override
    public void run(float deltaTime, BasicActionPayload rawPayload) {
        accumulatedTime += deltaTime;
        if (accumulatedTime >= interval) {
            action.extract_run(rawPayload);
            accumulatedTime %= interval;
        }
    }
}
