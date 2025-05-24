package org.toadallyarmed.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import org.toadallyarmed.component.BasicStateComponent;
import org.toadallyarmed.component.BasicStatefulRenderableComponent;
import org.toadallyarmed.component.WorldTransformComponent;
import org.toadallyarmed.state.BulletState;
import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.config.AnimationConfig;
import org.toadallyarmed.config.GameConfig;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.entity.EntityType;
import org.toadallyarmed.util.StateMachine;
import org.toadallyarmed.util.logger.Logger;
import org.toadallyarmed.util.rendering.AnimatedSprite;
import org.toadallyarmed.util.rendering.AnimatedStateSprite;

import java.util.HashMap;
import java.util.Map;

public class BulletFactory implements Disposable {
    private static final BulletFactory factoryInstance = new BulletFactory();

    private final Texture fireballTexture;
    private final AnimationFactory animationFactory=new AnimationFactory(new AnimationConfig(
        0.05f, new Vector2(0, 0), new Vector2(1, 1), 5, 1, false
    ));
    private final AnimatedStateSprite<BulletState> bulletAnimatedStateSprite, fireballAnimatedStateSprite;

    private BulletFactory(){
        Logger.trace("Initializing BulletFactory");

        fireballTexture  = new Texture("GameScreen/Fireball/fireball.png");
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
        StateMachine<BulletState> stateMachine = new StateMachine<>(BulletState.IDLE);
        stateMachine.addState(BulletState.IDLE, BulletState.IDLE);
        stateMachine.addState(BulletState.NONEXISTENT, BulletState.NONEXISTENT);
        BasicStateComponent<BulletState> state = new BasicStateComponent<>(stateMachine);
        BasicStatefulRenderableComponent<BulletState> renderable =
            new BasicStatefulRenderableComponent<>(
                transform,
                state,
                animatedStateSprite);
        return new Entity(EntityType.BULLET)
            .put(TransformComponent.class, transform)
            .put(StateComponent.class, state)
            .put(RenderableComponent.class, renderable);
    }
    private AnimatedStateSprite<BulletState> createAnimatedStateSprite(boolean real) {
        Map<BulletState, AnimatedSprite> animatedSprites = new HashMap<>();

        if (real) animatedSprites.put(BulletState.IDLE, animationFactory.Animation(fireballTexture, 0, 0, 4));
        else animatedSprites.put(BulletState.IDLE, AnimatedSprite.empty());
        animatedSprites.put(BulletState.NONEXISTENT, AnimatedSprite.empty());

        return new AnimatedStateSprite<>(animatedSprites);
    }
}
