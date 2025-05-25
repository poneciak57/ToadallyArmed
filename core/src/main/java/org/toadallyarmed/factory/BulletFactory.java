package org.toadallyarmed.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import org.toadallyarmed.component.BasicStateComponent;
import org.toadallyarmed.component.BasicStatefulRenderableComponent;
import org.toadallyarmed.component.WorldTransformComponent;
import org.toadallyarmed.state.BasicEntityState;
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
    private final AnimatedStateSprite<BasicEntityState> bulletAnimatedStateSprite, fireballAnimatedStateSprite;

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

    private Entity createBullet(AnimatedStateSprite<BasicEntityState> animatedStateSprite, Vector2 pos, GameConfig config) {
        Logger.trace("Creating Bullet Entity in factory");
        WorldTransformComponent transform = new WorldTransformComponent(pos, new Vector2(config.BulletSystemTickRate(), 0));
        StateMachine<BasicEntityState> stateMachine = new StateMachine<>(BasicEntityState.IDLE);
        stateMachine.addState(BasicEntityState.IDLE, BasicEntityState.IDLE);
        stateMachine.addState(BasicEntityState.NONEXISTENT, BasicEntityState.NONEXISTENT);
        BasicStateComponent<BasicEntityState> state = new BasicStateComponent<>(stateMachine);
        BasicStatefulRenderableComponent<BasicEntityState> renderable =
            new BasicStatefulRenderableComponent<>(
                transform,
                state,
                animatedStateSprite);
        return new Entity(EntityType.BULLET)
            .put(TransformComponent.class, transform)
            .put(StateComponent.class, state)
            .put(RenderableComponent.class, renderable);
    }
    private AnimatedStateSprite<BasicEntityState> createAnimatedStateSprite(boolean real) {
        Map<BasicEntityState, AnimatedSprite> animatedSprites = new HashMap<>();

        if (real) animatedSprites.put(BasicEntityState.IDLE, animationFactory.Animation(fireballTexture, 0, 0, 4));
        else animatedSprites.put(BasicEntityState.IDLE, AnimatedSprite.empty());
        animatedSprites.put(BasicEntityState.NONEXISTENT, AnimatedSprite.empty());

        return new AnimatedStateSprite<>(animatedSprites);
    }
}
