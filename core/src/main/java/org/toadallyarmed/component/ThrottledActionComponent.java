package org.toadallyarmed.component;

import org.toadallyarmed.component.action.payload.BasicActionPayload;
import org.toadallyarmed.component.interfaces.ActionComponent;
import org.toadallyarmed.util.action.Action;

public class ThrottledActionComponent implements ActionComponent {
    private final Action<?, BasicActionPayload> action;
    private final float interval;
    private float accumulatedTime;
    private float lastTime = 0f;

    public ThrottledActionComponent(float tickRate, Action<?, BasicActionPayload> action) {
        this.interval = 1f / tickRate;
        this.action = action;
        this.accumulatedTime = interval;
    }
    @Override
    public void run(float currentNano, BasicActionPayload rawPayload) {
        float deltaTime = (currentNano - lastTime) / 1_000_000_000f;
        this.lastTime = currentNano;
        accumulatedTime += deltaTime;
        if (accumulatedTime >= interval) {
            action.extract_run(rawPayload);
            accumulatedTime = 0.f;
        }
    }
}
