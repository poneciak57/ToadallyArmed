package org.toadallyarmed.component;

import org.toadallyarmed.component.interfaces.Component;
import org.toadallyarmed.util.logger.Logger;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.max;

public class HealthComponent implements Component {
    private final AtomicInteger health;
    private Runnable removeHealthAction;
    private Runnable noHealthAction;

    public HealthComponent(int initialHealth) {
        this.health = new AtomicInteger(initialHealth);
    }

    /**
     * removeHealthAction is <b>not</b> called, if noHealthAction is.
     */
    public HealthComponent setRemoveHealthAction(Runnable removeHealthAction) {
        this.removeHealthAction = removeHealthAction;
        return this;
    }

    /**
     * noHealthAction is called <b>instead</b> of removeHealthAction.
     */
    public HealthComponent setNoHealthAction(Runnable noHealthAction) {
        this.noHealthAction = noHealthAction;
        return this;
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
        if (noHealthAction != null && this.health.updateAndGet(x -> max(x - healthChange, 0)) <= 0)
            noHealthAction.run();
        else if (removeHealthAction != null)
            removeHealthAction.run();
        Logger.debug("Removing " + healthChange + " health. Health left: " + getHealth());
    }

    @Deprecated
    public AtomicInteger access() {
        return health;
    }
}
