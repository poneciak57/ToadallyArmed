package org.toadallyarmed.component;

import org.toadallyarmed.component.interfaces.Component;

import java.util.concurrent.atomic.AtomicInteger;

public class HealthComponent implements Component {
    private final AtomicInteger health;

    public HealthComponent(int health) {
        this.health = new AtomicInteger(health);
    }

    public AtomicInteger access() {
        return health;
    }
}
