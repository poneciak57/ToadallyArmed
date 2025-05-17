package org.toadallyarmed.component;

import org.toadallyarmed.component.action.payload.BasicActionPayload;
import org.toadallyarmed.component.interfaces.ActionComponent;
import org.toadallyarmed.util.action.Action;

import java.util.List;

public class SingleActionComponent implements ActionComponent {
    private final Action<?, BasicActionPayload> action;

    public SingleActionComponent(Action<?, BasicActionPayload> action) {
        this.action = action;
    }

    @Override
    public void run(BasicActionPayload rawPayload) {
        action.extract_run(rawPayload);
    }
}
