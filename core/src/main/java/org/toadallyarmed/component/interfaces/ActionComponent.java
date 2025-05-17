package org.toadallyarmed.component.interfaces;

import org.toadallyarmed.component.action.payload.BasicActionPayload;

public interface ActionComponent extends Component {
    void run(float deltaTime, BasicActionPayload rawPayload);
}
