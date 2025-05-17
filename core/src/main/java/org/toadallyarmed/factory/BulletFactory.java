package org.toadallyarmed.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import org.toadallyarmed.component.WorldTransformComponent;
import org.toadallyarmed.component.bullet.BulletRenderableComponent;
import org.toadallyarmed.component.bullet.BulletState;
import org.toadallyarmed.component.bullet.BulletStateComponent;
import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.config.GameConfig;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.entity.EntityType;
import org.toadallyarmed.util.logger.Logger;
import org.toadallyarmed.util.rendering.AnimatedSprite;
import org.toadallyarmed.util.rendering.AnimatedStateSprite;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BulletFactory implements Disposable {
    private static final BulletFactory factoryInstance = new BulletFactory();

    private final Texture fireballTexture;
    private final AnimatedStateSprite<BulletState> bulletAnimatedStateSprite, fireballAnimatedStateSprite;

    private BulletFactory(){
        Logger.trace("Initializing BulletFactory");

        fireballTexture  = new Texture("GameScreen/fireball/fireball.png");
        bulletAnimatedStateSprite = createAnimatedStateSprite(false);
        fireballAnimatedStateSprite=createAnimatedStateSprite(true);
        Logger.debug("Initialized BulletFactory successfully");
    }

    public static BulletFactory get() {
        return factoryInstance;
    }
    public Entity createBullet(Vector2 pos, GameConfig config){ return createBullet(bulletAnimatedStateSprite, pos, config);}
    public Entity createFireball(Vector2 pos, GameConfig config){return createBullet(fireballAnimatedStateSprite, pos, config);}

    @Override
    public void dispose() {
        Logger.trace("Disposing FireballFactory");
        fireballTexture.dispose();
    }

    private Entity createBullet(AnimatedStateSprite<BulletState> animatedStateSprite, Vector2 pos, GameConfig config) {
        Logger.trace("Creating Bullet Entity in factory");
        WorldTransformComponent transform = new WorldTransformComponent(pos, new Vector2(config.BulletSystemTickRate(), 0));
        BulletStateComponent BulletState = new BulletStateComponent();
        BulletRenderableComponent renderable =
            new BulletRenderableComponent(
                transform,
                BulletState,
                animatedStateSprite);
        return new Entity.EntityBuilder(EntityType.BULLET)
            .add(TransformComponent.class, transform)
            .add(StateComponent.class, BulletState)
            .add(RenderableComponent.class, renderable)
            .build();
    }
    private AnimatedStateSprite<BulletState> createAnimatedStateSprite(boolean real) {
        //Check
        final float FRAME_DURATION          = 0.12f;
        final Vector2 OFFSET                = new Vector2(0, 0);
        final Vector2 BASE_DIMENSIONS       = new Vector2(1, 1);

        Map<BulletState, AnimatedSprite> animatedSprites = new HashMap<>();
        TextureRegion[][] framesGrid;
        TextureRegion[] frames;
        Animation<TextureRegion> animation;

        framesGrid = TextureRegion.split(fireballTexture,
            fireballTexture.getWidth()/5,
            fireballTexture.getHeight());

        frames = Arrays.copyOfRange(framesGrid[0], 0, 4);
        animation = new Animation<>(FRAME_DURATION, frames);
        if (real) animatedSprites.put(BulletState.IDLE, new AnimatedSprite(animation, OFFSET, BASE_DIMENSIONS));
        else animatedSprites.put(BulletState.IDLE, AnimatedSprite.empty());
        animatedSprites.put(BulletState.NONEXISTENT, AnimatedSprite.empty());

        return new AnimatedStateSprite<>(animatedSprites);
    }
}
