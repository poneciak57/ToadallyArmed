package org.toadallyarmed.system;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.config.GameConfig;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.entity.EntityType;
import org.toadallyarmed.factory.HedgehogFactory;
import org.toadallyarmed.util.logger.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class EnemySpawnerSystem implements System {
    HedgehogFactory hedgehogFactory;
    GameConfig config;
    int spawnsPerWave, remainingSpawns, remainingWaves;
    boolean placingTime=true, first=true;

    public EnemySpawnerSystem(HedgehogFactory hedgehogFactory, GameConfig config) {
        this.hedgehogFactory=hedgehogFactory;
        this.config=config;
        spawnsPerWave=config.EnemySpawnerSystemHedgehogsPerWave();
        remainingSpawns=0;
        remainingWaves=2*config.EnemySpawnerSystemWaveQuantity();
    }

    @Override
    public void tick(float deltaTime, ConcurrentLinkedQueue<Entity> entities) {
        if (first) {
            first=false;
            return;
        }

        Logger.trace("EnemySpawnerSystem: placing an enemy");
        if(remainingWaves>0 || remainingSpawns>0){
            if (remainingSpawns == 0) {
                remainingSpawns = spawnsPerWave;
                placingTime = !placingTime;
                remainingWaves--;
            }
            remainingSpawns--;
            if (placingTime) {
                Vector2 pos = new Vector2(11, ThreadLocalRandom.current().nextInt(0, 5));
                entities.add(hedgehogFactory.createRandomHedgehog(pos, config));
            }
        }
        else {
            for (Entity entity : entities)
                if (!entity.isMarkedForRemoval() && entity.type() == EntityType.HEDGEHOG)
                    if (entity.isActive()) return;

            Logger.info("It's all done");
            entities.add(new Entity(EntityType.WINNING));
        }
    }
}
