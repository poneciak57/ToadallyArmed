package org.toadallyarmed.gameplay.frogs;

import org.toadallyarmed.core.components.Component;

public class FrogStateComponent extends Component {
    volatile FrogState generalState;
    volatile boolean isAttacked;
}
