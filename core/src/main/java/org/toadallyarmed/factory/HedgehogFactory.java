package org.toadallyarmed.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import org.toadallyarmed.component.*;
import org.toadallyarmed.component.action.*;
import org.toadallyarmed.component.interfaces.*;
import org.toadallyarmed.config.GameConfig;
import org.toadallyarmed.state.BooleanState;
import org.toadallyarmed.state.HedgehogState;
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
import java.util.concurrent.ThreadLocalRandom;

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
    public Entity createRandomHedgehog(Vector2 pos, GameConfig config) {
        int enemyType= ThreadLocalRandom.current().nextInt(0, 4);
        return switch (enemyType) {
            case 1 -> createFastHedgehog(pos, config.fastHedgehog());
            case 2 -> createStrongHedgehog(pos, config.strongHedgehog());
            case 3 -> createHealthyHedgehog(pos, config.healthyHedgehog());
            default -> createBasicHedgehog(pos, config.basicHedgehog());
        };
    }


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
        AliveEntityStateComponent<HedgehogState> state = new AliveEntityStateComponent<>(
            new StateMachine<>(HedgehogState.WALKING)
                .addState(HedgehogState.IDLE, HedgehogState.IDLE, true)
                .addState(HedgehogState.WALKING, HedgehogState.WALKING, true)
                .addState(HedgehogState.ACTION, HedgehogState.IDLE, true)
                .addState(HedgehogState.DYING, HedgehogState.NONEXISTENT, entity.getMarkForRemovalRunnable(), false)
                .addState(HedgehogState.NONEXISTENT, HedgehogState.NONEXISTENT, false)
        );
        HealthComponent health =
            new HealthComponent(config.hp())
                .setRemoveHealthAction(() -> state.getIsAttackedStateMachine().setNextTmpState(BooleanState.TRUE))
                .setNoHealthAction(() -> {
                    state.getIsAttackedStateMachine().setNextTmpState(BooleanState.TRUE);
                    state.getGeneralStateMachine().setNextTmpState(HedgehogState.DYING);
                });
        AliveEntityRenderableComponent<HedgehogState> renderable =
            new AliveEntityRenderableComponent<>(
                transform,
                state,
                animatedStateSprite);
        ColliderComponent colliderComponent = new ColliderComponent(
            List.of(
                new BasicNoActionColliderEntry(
                    new RectangleShape(1f, 1f),
                    ColliderType.ENTITY
                ),
                new ThrottledCollisionActionEntry(
                    config.action_speed(),
                    new BasicColliderActionEntry(
                        new RectangleShape(1f, 0.5f, 0.0f, -0.25f),
                        new HeadgehogAttackCollisionAction(config.damage()),
                        ColliderType.ACTION,
                        (otherType, otherColliderType) ->
                            otherType.equals(EntityType.FROG) && otherColliderType.equals(ColliderType.ENTITY)
                    )
                )
            )
        );
        var timerAction = new HeadgehogAttackTimerComponent();
        var actionReset = new HeadgehogAttackResetAction(
            (long) (1_000_000_000 * config.action_speed() * 1.2),
                new Vector2(config.speed(), 0)
        );
        entity
            .put(TransformComponent.class, transform)
            .put(StateComponent.class, state)
            .put(HeadgehogAttackTimerComponent.class, timerAction)
            .put(ActionComponent.class, new SingleActionComponent(actionReset))
            .put(RenderableComponent.class, renderable)
            .put(HealthComponent.class, health)
            .put(ColliderComponent.class, colliderComponent);
        return entity;
    }

    private AnimatedStateSprite<HedgehogState> createAnimatedStateSprite(Texture texture) {
        Map<HedgehogState, AnimatedSprite> animatedSprites = new HashMap<>();
        animatedSprites.put(HedgehogState.IDLE, AnimationFactory.Animation(texture, 0, 4, 6));
        animatedSprites.put(HedgehogState.WALKING, AnimationFactory.Animation(texture, 1, 0, 6));
        animatedSprites.put(HedgehogState.ACTION, AnimationFactory.Animation(texture, 0, 4, 6));
        animatedSprites.put(HedgehogState.DYING, AnimationFactory.Animation(texture, 3, 1, 6));
        animatedSprites.put(HedgehogState.NONEXISTENT, AnimatedSprite.empty());

        return new AnimatedStateSprite<>(animatedSprites);
    }
}
