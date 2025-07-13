package org.toadallyarmed.entity.character.frog;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import org.toadallyarmed.base.health.HealthComponent;
import org.toadallyarmed.base.action.ActionComponent;
import org.toadallyarmed.base.physics.collision.*;
import org.toadallyarmed.base.render.RenderableComponent;
import org.toadallyarmed.base.state.StateComponent;
import org.toadallyarmed.base.transform.TransformComponent;
import org.toadallyarmed.base.action.ThrottledActionComponent;
import org.toadallyarmed.base.transform.WorldTransformComponent;
import org.toadallyarmed.entity.passive.bullet.BulletFactory;
import org.toadallyarmed.entity.character.aliveentity.AliveEntityRenderableComponent;
import org.toadallyarmed.entity.character.aliveentity.AliveEntityStateComponent;
import org.toadallyarmed.entity.character.frog.bard.BardActionPerformer;
import org.toadallyarmed.util.render.AnimationFactory;
import org.toadallyarmed.util.state.BooleanState;
import org.toadallyarmed.util.render.AnimationConfig;
import org.toadallyarmed.entity.character.CharacterConfig;
import org.toadallyarmed.base.entity.Entity;
import org.toadallyarmed.base.entity.EntityType;
import org.toadallyarmed.util.state.StateMachine;
import org.toadallyarmed.util.collision.RectangleShape;
import org.toadallyarmed.util.render.AnimatedSprite;
import org.toadallyarmed.util.render.AnimatedStateSprite;
import org.toadallyarmed.util.log.Logger;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.toadallyarmed.scene.gameplay.GameConfig.TILE_HEIGHT;

public class FrogFactory implements Disposable {
    private static final FrogFactory factoryInstance = new FrogFactory();
    private final AnimationFactory animationFactory = new AnimationFactory(new AnimationConfig(
        0.08f, new Vector2(-0.4f, -0.53f), new Vector2(2, 2), 9, 5, false
    ));

    private final Texture basicFrogTexture;
    private final Texture knightFrogTexture;
    private final Texture bardFrogTexture;
    private final Texture tankFrogTexture;
    private final Texture wizardFrogTexture;
    private final AnimatedStateSprite<FrogState> basicFrogAnimatedStateSprite;
    private final AnimatedStateSprite<FrogState> knightFrogAnimatedStateSprite;
    private final AnimatedStateSprite<FrogState> bardFrogAnimatedStateSprite;
    private final AnimatedStateSprite<FrogState> tankFrogAnimatedStateSprite;
    private final AnimatedStateSprite<FrogState> wizardFrogAnimatedStateSprite;

    private FrogFactory() {
        Logger.trace("Initializing FrogFactory");

        basicFrogTexture  = new Texture("assets/Frogs/basicFrog.png");
        knightFrogTexture = new Texture("assets/Frogs/knightFrog.png");
        bardFrogTexture   = new Texture("assets/Frogs/bardFrog.png");
        tankFrogTexture   = new Texture("assets/Frogs/tankFrog.png");
        wizardFrogTexture = new Texture("assets/Frogs/wizardFrog.png");

        basicFrogAnimatedStateSprite   = createAnimatedStateSprite(basicFrogTexture);
        knightFrogAnimatedStateSprite  = createAnimatedStateSprite(knightFrogTexture);
        bardFrogAnimatedStateSprite    = createAnimatedStateSprite(bardFrogTexture);
        tankFrogAnimatedStateSprite    = createAnimatedStateSprite(tankFrogTexture);
        wizardFrogAnimatedStateSprite  = createAnimatedStateSprite(wizardFrogTexture);

        Logger.debug("Initialized FrogFactory successfully");
    }

    public static FrogFactory get() {
        return factoryInstance;
    }

    public Entity createBasicFrog(Vector2 pos, CharacterConfig config) { return createFrog(basicFrogAnimatedStateSprite, pos, config, new ArrayList<>()); }
    public Entity createKnightFrog(Vector2 pos, CharacterConfig config) {
        List<ColliderActionEntry> colliders = new ArrayList<>(List.of(
            new ThrottledCollisionActionEntry(
                config.action_speed(),
                new BasicColliderActionEntry(
                    new RectangleShape(config.attackRange(), TILE_HEIGHT / 2, 0.f, -TILE_HEIGHT / 4),
                    new FrogAttackCollisionActionPerformer(
                        vector2 -> BulletFactory.get().createBullet(
                            vector2.add(config.bulletConfig().offsetX(), config.bulletConfig().offsetY()),
                            config.bulletConfig().speed(),
                            config.damage(),
                            EntityType.HEDGEHOG
                        )
                    ),
                    ColliderType.ACTION,
                    new BasicCollisionActionFilter(EntityType.HEDGEHOG, ColliderType.ENTITY)
                )
            )
        ));

        return createFrog(knightFrogAnimatedStateSprite, pos, config, colliders);
    }
    public Entity createBardFrog(Vector2 pos, CharacterConfig config) {
        var entity= createFrog(bardFrogAnimatedStateSprite, pos, config, new ArrayList<>());

        entity.put(ActionComponent.class, new ThrottledActionComponent(
            config.action_speed(), new BardActionPerformer()
        ));

        return entity;
    }
    public Entity createTankFrog(Vector2 pos, CharacterConfig config) { return createFrog(tankFrogAnimatedStateSprite, pos, config, new ArrayList<>()); }
    public Entity createWizardFrog(Vector2 pos, CharacterConfig config) {
        List<ColliderActionEntry> colliders = new ArrayList<>(List.of(
            new ThrottledCollisionActionEntry(
                config.action_speed(),
                new BasicColliderActionEntry(
                    new RectangleShape(config.attackRange(), TILE_HEIGHT / 2, 0.f, -TILE_HEIGHT / 4),
                    new FrogAttackCollisionActionPerformer(
                        vector2 -> BulletFactory.get().createFireball(
                            vector2.add(config.bulletConfig().offsetX(), config.bulletConfig().offsetY()),
                            config.bulletConfig().speed(),
                            config.damage(),
                            EntityType.HEDGEHOG
                        )
                    ),
                    ColliderType.ACTION,
                    new BasicCollisionActionFilter(EntityType.HEDGEHOG, ColliderType.ENTITY)
                )
            )
        ));

        return createFrog(wizardFrogAnimatedStateSprite, pos, config, colliders);
    }

    @Override
    public void dispose() {
        Logger.trace("Disposing FrogFactory");
        basicFrogTexture.dispose();
        knightFrogTexture.dispose();
        bardFrogTexture.dispose();
        tankFrogTexture.dispose();
        wizardFrogTexture.dispose();
    }


    private Entity createFrog(AnimatedStateSprite<FrogState> animatedStateSprite, Vector2 pos, CharacterConfig config, List<ColliderActionEntry> colliderActions) {
        Logger.trace("Creating Frog Entity in factory");
        Entity entity = new Entity(EntityType.FROG);
        WorldTransformComponent transform = new WorldTransformComponent(pos, new Vector2(config.speed(), 0));
        AliveEntityStateComponent<FrogState> state = new AliveEntityStateComponent<>(
            new StateMachine<>(FrogState.IDLE)
                .addState(FrogState.IDLE, FrogState.IDLE, true)
                .addState(FrogState.ACTION, FrogState.IDLE, true)
                .addState(FrogState.HOP, FrogState.IDLE, true)
                .addState(FrogState.DYING, FrogState.NONEXISTENT, entity.getMarkForRemovalRunnable(), false)
                .addState(FrogState.NONEXISTENT, FrogState.NONEXISTENT, false)
        );
        HealthComponent health =
            new HealthComponent(config.hp())
                .setRemoveHealthAction(() -> state.getIsAttackedStateMachine().setNextTmpState(BooleanState.TRUE))
                .setNoHealthAction(() -> state.getGeneralStateMachine().setNextTmpState(FrogState.DYING));
        AliveEntityRenderableComponent<FrogState> renderable =
            new AliveEntityRenderableComponent<>(
                transform,
                state,
                animatedStateSprite);
        colliderActions.add(
            new BasicNoActionColliderEntry(
                new RectangleShape(1f, 1f),
                ColliderType.ENTITY
            )
        );
        ColliderComponent collider = new ColliderComponent(colliderActions);
        entity
            .put(TransformComponent.class, transform)
            .put(StateComponent.class, state)
            .put(RenderableComponent.class, renderable)
            .put(HealthComponent.class, health)
            .put(ColliderComponent.class, collider);
        return entity;
    }

    private AnimatedStateSprite<FrogState> createAnimatedStateSprite(Texture texture) {
        Map<FrogState, AnimatedSprite> animatedSprites = new HashMap<>();

        animatedSprites.put(FrogState.IDLE, animationFactory.animation(texture, 0, 0, 8));
        animatedSprites.put(FrogState.HOP, animationFactory.animation(texture, 1, 0, 7));
        animatedSprites.put(FrogState.ACTION, animationFactory.animation(texture, 2, 0, 6));
        animatedSprites.put(FrogState.DYING, animationFactory.animation(texture, 4, 0, 9));
        animatedSprites.put(FrogState.NONEXISTENT, AnimatedSprite.empty());
        return new AnimatedStateSprite<>(animatedSprites);
    }
}
