package org.toadallyarmed.system;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.config.GameConfig;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.factory.HedgehogFactory;
import org.toadallyarmed.util.logger.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class EnemySpawnerSystem implements System {
    HedgehogFactory hedgehogFactory;
    GameConfig config;
    int remainingSpawns;

    public EnemySpawnerSystem(HedgehogFactory hedgehogFactory, GameConfig config) {
        this.hedgehogFactory=hedgehogFactory;
        this.config=config;
        this.remainingSpawns =config.EnemySpawnerSystemQuantity();
    }

    @Override
    public void tick(float deltaTime, ConcurrentLinkedQueue<Entity> entities) {
        Logger.trace("EnemySpawnerSystem: placing an enemy");
        if (remainingSpawns>0) {
            Vector2 pos = new Vector2(11, ThreadLocalRandom.current().nextInt(0, 5));
            entities.add(hedgehogFactory.createRandomHedgehog(pos, config));
            remainingSpawns--;
        }
    }
}
