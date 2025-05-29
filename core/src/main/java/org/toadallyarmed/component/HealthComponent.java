package org.toadallyarmed.component;

import org.toadallyarmed.component.interfaces.Component;
import org.toadallyarmed.util.logger.Logger;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.max;

public class HealthComponent implements Component {
    private final AtomicInteger health;
    private final Runnable noHealthAction;

    public HealthComponent(int health) {
        this(health, null);
    }

    public HealthComponent(int health, Runnable noHealthAction) {
        this.health = new AtomicInteger(health);
        this.noHealthAction = noHealthAction;
    }

    public int getHealth() {
        return health.get();
    }

    public void addHealth(int healthChange) {
        Logger.errorIfNot(healthChange > 0, "Health change must be greater than 0.");
        this.health.addAndGet(healthChange);
    }

    public void removeHealth(int healthChange) {
        Logger.errorIfNot(healthChange > 0, "Health change must be greater than 0.");
        if (this.health.updateAndGet(x -> max(x - healthChange, 0)) <= 0 && noHealthAction != null)
            noHealthAction.run();
        Logger.debug("Removing " + healthChange + " health. Health left: " + getHealth());
    }

    @Deprecated
    public AtomicInteger access() {
        return health;
    }
}
