package org.toadallyarmed.component;

import org.toadallyarmed.component.interfaces.Component;

import java.util.concurrent.atomic.AtomicLong;

public class HeadgehogAttackTimerComponent implements Component {
    private final AtomicLong lastActionNano = new AtomicLong(0);

    public void updateLastActionNano() {
        lastActionNano.set(java.lang.System.nanoTime());
    }

    public long lastActionNano() {
        return lastActionNano.get();
    }
}
