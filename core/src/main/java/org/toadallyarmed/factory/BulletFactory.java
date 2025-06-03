package org.toadallyarmed.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import org.toadallyarmed.component.BasicStateComponent;
import org.toadallyarmed.component.BasicStatefulRenderableComponent;
import org.toadallyarmed.component.ColliderComponent;
import org.toadallyarmed.component.WorldTransformComponent;
import org.toadallyarmed.component.action.BasicColliderActionEntry;
import org.toadallyarmed.component.action.BasicCollisionActionFilter;
import org.toadallyarmed.component.action.BulletCollisionAction;
import org.toadallyarmed.component.interfaces.ColliderType;
import org.toadallyarmed.state.BasicEntityState;
import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.config.AnimationConfig;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.entity.EntityType;
import org.toadallyarmed.util.StateMachine;
import org.toadallyarmed.util.action.CollisionActionFilter;
import org.toadallyarmed.util.collision.RectangleShape;
import org.toadallyarmed.util.logger.Logger;
import org.toadallyarmed.util.rendering.AnimatedSprite;
import org.toadallyarmed.util.rendering.AnimatedStateSprite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.toadallyarmed.config.GameConfig.TILE_HEIGHT;
import static org.toadallyarmed.config.GameConfig.TILE_WIDTH;

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
    public Entity createBullet(Vector2 pos, float speed, int damage, EntityType targetEntityType){ return createBullet(bulletAnimatedStateSprite, pos, speed, damage, targetEntityType);}
    public Entity createFireball(Vector2 pos, float speed, int damage, EntityType targetEntityType){return createBullet(fireballAnimatedStateSprite, pos, speed, damage, targetEntityType);}

    @Override
    public void dispose() {
        Logger.trace("Disposing FireballFactory");
        fireballTexture.dispose();
    }

    private Entity createBullet(AnimatedStateSprite<BasicEntityState> animatedStateSprite, Vector2 pos, float speed, int damage, EntityType targetEntityType) {
        Logger.trace("Creating Bullet Entity in factory");
        Logger.debug("Creating bullet at pos: " + pos);
        Entity entity = new Entity(EntityType.BULLET);
        WorldTransformComponent transform = new WorldTransformComponent(pos, new Vector2(speed, 0));
        BasicStateComponent<BasicEntityState> state = new BasicStateComponent<>(
            new StateMachine<>(BasicEntityState.IDLE)
                .addState(BasicEntityState.IDLE, BasicEntityState.IDLE, true)
                .addState(BasicEntityState.NONEXISTENT, BasicEntityState.NONEXISTENT, false)
        );
        ColliderComponent colliders = new ColliderComponent(
            List.of(
                new BasicColliderActionEntry(
                    new RectangleShape(TILE_WIDTH/2, TILE_HEIGHT/2, -TILE_WIDTH/4, -TILE_HEIGHT/4),
                    new BulletCollisionAction(damage, entity.getMarkForRemovalRunnable()),
                    ColliderType.ACTION,
                    new BasicCollisionActionFilter(targetEntityType, ColliderType.ENTITY)
                )
            )
        );
        BasicStatefulRenderableComponent<BasicEntityState> renderable =
            new BasicStatefulRenderableComponent<>(
                transform,
                state,
                animatedStateSprite);
        entity
            .put(TransformComponent.class, transform)
            .put(StateComponent.class, state)
            .put(ColliderComponent.class, colliders)
            .put(RenderableComponent.class, renderable);
        return entity;
    }
    private AnimatedStateSprite<BasicEntityState> createAnimatedStateSprite(boolean real) {
        Map<BasicEntityState, AnimatedSprite> animatedSprites = new HashMap<>();

        if (real) animatedSprites.put(BasicEntityState.IDLE, animationFactory.Animation(fireballTexture, 0, 0, 5));
        else animatedSprites.put(BasicEntityState.IDLE, AnimatedSprite.empty());
        animatedSprites.put(BasicEntityState.NONEXISTENT, AnimatedSprite.empty());

        return new AnimatedStateSprite<>(animatedSprites);
    }
}
