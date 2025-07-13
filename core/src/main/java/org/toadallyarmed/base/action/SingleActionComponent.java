package org.toadallyarmed.base.action;

import org.toadallyarmed.util.action.Action;

public class SingleActionComponent implements ActionComponent {
    private final Action<BasicActionPayload> action;

    public SingleActionComponent(Action<BasicActionPayload> action) {
        this.action = action;
    }

    @Override
    public void run(float deltaTime, BasicActionPayload rawPayload) {
        action.run(rawPayload);
    }
}
