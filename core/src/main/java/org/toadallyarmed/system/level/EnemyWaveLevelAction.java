package org.toadallyarmed.system.level;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.config.GameConfig;
import org.toadallyarmed.util.action.PayloadExtractor;

import java.util.concurrent.ThreadLocalRandom;

public class EnemyWaveLevelAction implements LevelAction<EnemyWaveLevelActionPayload> {
    final boolean waitForEnemiesDeath;

    int remainingSpawns;
    int remainingAlive = 0;

    public EnemyWaveLevelAction(boolean waitForEnemiesDeath, int spawnsCount) {
        this.waitForEnemiesDeath=waitForEnemiesDeath;
        remainingSpawns=spawnsCount;
    }

    @Override
    public boolean isDone() {
        return remainingSpawns <= 0 && (remainingAlive == 0 || !waitForEnemiesDeath);
    }

    @Override
    public void run(EnemyWaveLevelActionPayload payload) {
        if (remainingSpawns <= 0)
            return;
        Vector2 pos = new Vector2(
            GameConfig.BOARD_WIDTH,
            ThreadLocalRandom.current().nextInt(0, GameConfig.BOARD_HEIGHT));
        payload.entities().add(
            payload.hedgehogFactory().createRandomHedgehog(pos, payload.config())
                .setOnRemoveAction(() -> --remainingAlive));
        --remainingSpawns;
        ++remainingAlive;
    }

    @Override
    public PayloadExtractor<EnemyWaveLevelActionPayload, BasicLevelActionPayload> extractor() {
        return EnemyWaveLevelActionPayload.EXTRACTOR;
    }
}
