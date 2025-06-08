package org.toadallyarmed.system.level;

import org.toadallyarmed.util.action.PayloadExtractor;

public class EmptyLevelAction implements LevelAction<EmptyLevelActionPayload> {
    int remainingTicks;

    public EmptyLevelAction(int ticksCount) {
        this.remainingTicks = ticksCount;
    }

    @Override
    public boolean isDone() {
        return remainingTicks == 0;
    }

    @Override
    public void run(EmptyLevelActionPayload payload) {
        if (isDone())
            return;
        --remainingTicks;
    }

    @Override
    public PayloadExtractor<EmptyLevelActionPayload, BasicLevelActionPayload> extractor() {
        return EmptyLevelActionPayload.EXTRACTOR;
    }
}
