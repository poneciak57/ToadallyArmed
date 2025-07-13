package org.toadallyarmed.entity.passive.bullet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import org.toadallyarmed.base.state.BasicStateComponent;
import org.toadallyarmed.base.render.BasicStatefulRenderableComponent;
import org.toadallyarmed.base.physics.collision.ColliderComponent;
import org.toadallyarmed.base.transform.WorldTransformComponent;
import org.toadallyarmed.base.physics.collision.BasicColliderActionEntry;
import org.toadallyarmed.base.physics.collision.BasicCollisionActionFilter;
import org.toadallyarmed.base.physics.collision.ColliderType;
import org.toadallyarmed.base.entity.BasicEntityState;
import org.toadallyarmed.base.render.RenderableComponent;
import org.toadallyarmed.base.state.StateComponent;
import org.toadallyarmed.base.transform.TransformComponent;
import org.toadallyarmed.util.render.AnimationConfig;
import org.toadallyarmed.base.entity.Entity;
import org.toadallyarmed.base.entity.EntityType;
import org.toadallyarmed.util.render.AnimationFactory;
import org.toadallyarmed.util.state.StateMachine;
import org.toadallyarmed.util.collision.RectangleShape;
import org.toadallyarmed.util.log.Logger;
import org.toadallyarmed.util.render.AnimatedSprite;
import org.toadallyarmed.util.render.AnimatedStateSprite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.toadallyarmed.scene.gameplay.GameConfig.TILE_HEIGHT;
import static org.toadallyarmed.scene.gameplay.GameConfig.TILE_WIDTH;

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
                    new BulletCollisionActionPerformer(damage, entity.getMarkForRemovalRunnable()),
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
