package org.toadallyarmed.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import org.toadallyarmed.component.*;
import org.toadallyarmed.component.action.BasicColliderActionEntry;
import org.toadallyarmed.component.action.BasicNonActionColliderEntry;
import org.toadallyarmed.component.interfaces.ColliderType;
import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.state.HedgehogState;
import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.config.AnimationConfig;
import org.toadallyarmed.config.CharacterConfig;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.entity.EntityType;
import org.toadallyarmed.util.StateMachine;
import org.toadallyarmed.util.collision.RectangleShape;
import org.toadallyarmed.util.rendering.AnimatedSprite;
import org.toadallyarmed.util.rendering.AnimatedStateSprite;
import org.toadallyarmed.util.logger.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HedgehogFactory implements Disposable {
    private static final HedgehogFactory factoryInstance = new HedgehogFactory();
    private final AnimationFactory AnimationFactory=new AnimationFactory(new AnimationConfig(
        0.12f, new Vector2(0, 0.1f), new Vector2(1.5f, 1.5f), 6, 4, true
    ));

    private final Texture basicHedgehogTexture;
    private final Texture fastHedgehogTexture;
    private final Texture strongHedgehogTexture;
    private final Texture healthyHedgehogTexture;
    private final AnimatedStateSprite<HedgehogState> basicHedgehogAnimatedStateSprite;
    private final AnimatedStateSprite<HedgehogState> fastHedgehogAnimatedStateSprite;
    private final AnimatedStateSprite<HedgehogState> strongHedgehogAnimatedStateSprite;
    private final AnimatedStateSprite<HedgehogState> healthyHedgehogAnimatedStateSprite;

    private HedgehogFactory() {
        Logger.trace("Initializing HedgehogFactory");

        basicHedgehogTexture    = new Texture("GameScreen/Hedgehogs/basicHedgehog.png");
        fastHedgehogTexture     = new Texture("GameScreen/Hedgehogs/fastHedgehog.png");
        strongHedgehogTexture   = new Texture("GameScreen/Hedgehogs/strongHedgehog.png");
        healthyHedgehogTexture  = new Texture("GameScreen/Hedgehogs/healthyHedgehog.png");

        basicHedgehogAnimatedStateSprite   = createAnimatedStateSprite(basicHedgehogTexture);
        fastHedgehogAnimatedStateSprite    = createAnimatedStateSprite(fastHedgehogTexture);
        strongHedgehogAnimatedStateSprite  = createAnimatedStateSprite(strongHedgehogTexture);
        healthyHedgehogAnimatedStateSprite = createAnimatedStateSprite(healthyHedgehogTexture);

        Logger.debug("Initialized HedgehogFactory successfully");
    }

    public static HedgehogFactory get() {
        return factoryInstance;
    }

    public Entity createBasicHedgehog(Vector2 pos, CharacterConfig config) { return createHedgehog(basicHedgehogAnimatedStateSprite, pos, config); }
    public Entity createFastHedgehog(Vector2 pos, CharacterConfig config) { return createHedgehog(fastHedgehogAnimatedStateSprite, pos, config); }
    public Entity createStrongHedgehog(Vector2 pos, CharacterConfig config) { return createHedgehog(strongHedgehogAnimatedStateSprite, pos, config); }
    public Entity createHealthyHedgehog(Vector2 pos, CharacterConfig config) { return createHedgehog(healthyHedgehogAnimatedStateSprite, pos, config); }

    @Override
    public void dispose() {
        Logger.trace("Disposing HedgehogFactory");
        basicHedgehogTexture.dispose();
        fastHedgehogTexture.dispose();
        strongHedgehogTexture.dispose();
        healthyHedgehogTexture.dispose();
    }


    private Entity createHedgehog(AnimatedStateSprite<HedgehogState> animatedStateSprite, Vector2 pos, CharacterConfig config) {
        Logger.trace("Creating Hedgehog Entity in factory");
        Entity entity = new Entity(EntityType.HEDGEHOG);
        WorldTransformComponent transform = new WorldTransformComponent(pos, new Vector2(config.speed(), 0.f));
        HealthComponent health = new HealthComponent(config.hp());
        StateMachine<HedgehogState> generalStateMachine = new StateMachine<>(HedgehogState.WALKING);
        generalStateMachine.addState(HedgehogState.IDLE, HedgehogState.IDLE);
        generalStateMachine.addState(HedgehogState.WALKING, HedgehogState.WALKING);
        generalStateMachine.addState(HedgehogState.ACTION, HedgehogState.IDLE);
        generalStateMachine.addState(HedgehogState.DYING, HedgehogState.NONEXISTENT, entity.getMarkForRemovalRunnable());
        generalStateMachine.addState(HedgehogState.NONEXISTENT, HedgehogState.NONEXISTENT);
        AliveEntityStateComponent<HedgehogState> state = new AliveEntityStateComponent<>(generalStateMachine);
        AliveEntityRenderableComponent<HedgehogState> renderable =
            new AliveEntityRenderableComponent<>(
                transform,
                state,
                animatedStateSprite);
        ColliderComponent colliderComponent = new ColliderComponent(
            List.of(
                new BasicNonActionColliderEntry(
                    new RectangleShape(1f, 1f),
                    ColliderType.ENTITY
                )
            )
        );
        entity
            .put(TransformComponent.class, transform)
            .put(StateComponent.class, state)
            .put(RenderableComponent.class, renderable)
            .put(HealthComponent.class, health)
            .put(ColliderComponent.class, colliderComponent);
        return entity;
    }

    private AnimatedStateSprite<HedgehogState> createAnimatedStateSprite(Texture texture) {
        Map<HedgehogState, AnimatedSprite> animatedSprites = new HashMap<>();
        animatedSprites.put(HedgehogState.IDLE, AnimationFactory.Animation(texture, 0, 4, 5));
        animatedSprites.put(HedgehogState.WALKING, AnimationFactory.Animation(texture, 1, 0, 5));
        animatedSprites.put(HedgehogState.ACTION, AnimationFactory.Animation(texture, 0, 4, 5));
        animatedSprites.put(HedgehogState.DYING, AnimationFactory.Animation(texture, 3, 1, 5));
        animatedSprites.put(HedgehogState.NONEXISTENT, AnimatedSprite.empty());

        return new AnimatedStateSprite<>(animatedSprites);
    }
}
