package org.toadallyarmed.system;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.config.GameConfig;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.factory.HedgehogFactory;
import org.toadallyarmed.util.logger.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class EnemyRenderSystem implements System {
    HedgehogFactory hedgehogFactory;
    GameConfig config;
    int cnt;

    public EnemyRenderSystem(HedgehogFactory hedgehogFactory, GameConfig config) {
        this.hedgehogFactory=hedgehogFactory;
        this.config=config;
        this.cnt=config.EnemyRenderSystemQuantity();
    }

    @Override
    public void tick(float deltaTime, ConcurrentLinkedQueue<Entity> entities) {
        Logger.trace("EnemyRenderSystem: tick");
        if (cnt>0) {
            Vector2 pos = new Vector2(10, ThreadLocalRandom.current().nextInt(0, 5));
            entities.add(hedgehogFactory.createRandomHedgehog(pos, config));
            cnt--;
        }
    }
}
