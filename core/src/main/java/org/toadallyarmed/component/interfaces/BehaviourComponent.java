package org.toadallyarmed.component.interfaces;

import org.toadallyarmed.entity.Entity;

public interface BehaviourComponent extends Component {
    void tick(float deltaTime, Entity current);
}
