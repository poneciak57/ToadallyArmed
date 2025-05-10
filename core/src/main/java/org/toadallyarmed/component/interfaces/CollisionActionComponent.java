package org.toadallyarmed.component.interfaces;

import org.toadallyarmed.entity.Entity;

public interface CollisionActionComponent extends Component {
    /// Action that will be performed with each entity that current one collides
    void collide(Entity other);
}
