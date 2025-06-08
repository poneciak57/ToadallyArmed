package org.toadallyarmed.system.level;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class Level {
    Queue<LevelAction<?>> actions = new LinkedList<>();

    public Level addAction(LevelAction<?> action) {
        actions.add(action);
        return this;
    }

    public Optional<LevelAction<?>> getActiveAction() {
        while (!actions.isEmpty() && actions.peek().isDone()) {
            actions.remove();
        }
        if (actions.isEmpty()) return Optional.empty();
        return Optional.of(actions.peek());
    }
}
