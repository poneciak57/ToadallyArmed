package org.toadallyarmed.component;

import org.toadallyarmed.component.interfaces.Component;

import java.util.concurrent.atomic.AtomicInteger;

public class WalletComponent implements Component {
    private final AtomicInteger counter;

    public WalletComponent(int initialValue) {
        counter = new AtomicInteger(initialValue);
    }

    public int currentMoney() { return counter.get(); }
    public void increase(int amount) { counter.addAndGet(amount); }
    public void pay(int amount) {counter.addAndGet(-amount);}
}
