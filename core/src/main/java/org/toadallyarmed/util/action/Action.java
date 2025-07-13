package org.toadallyarmed.util.action;

public interface Action <Payload> {
    void run(Payload payload);
}
