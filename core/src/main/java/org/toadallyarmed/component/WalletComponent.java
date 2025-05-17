package org.toadallyarmed.component;

import org.toadallyarmed.component.interfaces.Component;

import java.util.concurrent.atomic.AtomicInteger;

public class WalletComponent implements Component {
    private final AtomicInteger counter;

    public WalletComponent(int initialValue) {
        counter = new AtomicInteger(initialValue);
    }

    public AtomicInteger access() {
        return counter;
    }
    public void pay(int amount) {counter.addAndGet(-amount);}
}
