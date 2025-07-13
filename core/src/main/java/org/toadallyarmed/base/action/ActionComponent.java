package org.toadallyarmed.base.action;

import org.toadallyarmed.base.component.Component;

public interface ActionComponent extends Component {
    void run(float currentNano, BasicActionPayload rawPayload);
}
