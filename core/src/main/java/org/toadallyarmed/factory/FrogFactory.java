package org.toadallyarmed.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import org.toadallyarmed.component.*;
import org.toadallyarmed.component.action.*;
import org.toadallyarmed.component.interfaces.*;
import org.toadallyarmed.state.FrogState;
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

import static org.toadallyarmed.config.GameConfig.TILE_HEIGHT;
import static org.toadallyarmed.config.GameConfig.TILE_WIDTH;

public class FrogFactory implements Disposable {
    private static final FrogFactory factoryInstance = new FrogFactory();
    private final AnimationFactory animationFactory = new AnimationFactory(new AnimationConfig(
        0.08f, new Vector2(-0.4f, -0.53f), new Vector2(2, 2), 9, 5, false
    ));

    private final Texture basicFrogTexture;
    private final Texture knightFrogTexture;
    private final Texture moneyFrogTexture;
    private final Texture tankFrogTexture;
    private final Texture wizardFrogTexture;
    private final AnimatedStateSprite<FrogState> basicFrogAnimatedStateSprite;
    private final AnimatedStateSprite<FrogState> knightFrogAnimatedStateSprite;
    private final AnimatedStateSprite<FrogState> moneyFrogAnimatedStateSprite;
    private final AnimatedStateSprite<FrogState> tankFrogAnimatedStateSprite;
    private final AnimatedStateSprite<FrogState> wizardFrogAnimatedStateSprite;

    private FrogFactory() {
        Logger.trace("Initializing FrogFactory");

        basicFrogTexture  = new Texture("GameScreen/Frogs/basicFrog.png");
        knightFrogTexture = new Texture("GameScreen/Frogs/knightFrog.png");
        moneyFrogTexture  = new Texture("GameScreen/Frogs/moneyFrog.png");
        tankFrogTexture   = new Texture("GameScreen/Frogs/tankFrog.png");
        wizardFrogTexture = new Texture("GameScreen/Frogs/wizardFrog.png");

        basicFrogAnimatedStateSprite   = createAnimatedStateSprite(basicFrogTexture);
        knightFrogAnimatedStateSprite  = createAnimatedStateSprite(knightFrogTexture);
        moneyFrogAnimatedStateSprite   = createAnimatedStateSprite(moneyFrogTexture);
        tankFrogAnimatedStateSprite    = createAnimatedStateSprite(tankFrogTexture);
        wizardFrogAnimatedStateSprite  = createAnimatedStateSprite(wizardFrogTexture);

        Logger.debug("Initialized FrogFactory successfully");
    }

    public static FrogFactory get() {
        return factoryInstance;
    }

    public Entity createBasicFrog(Vector2 pos, CharacterConfig config) { return createFrog(basicFrogAnimatedStateSprite, pos, config); }
    public Entity createKnightFrog(Vector2 pos, CharacterConfig config) { return createFrog(knightFrogAnimatedStateSprite, pos, config); }
    public Entity createMoneyFrog(Vector2 pos, CharacterConfig config) {
        var entity= createFrog(moneyFrogAnimatedStateSprite, pos, config);

        entity.put(ActionComponent.class, new ThrottledActionComponent(
            config.atk_speed(), new BardAction()
        ));

        return entity;
    }
    public Entity createTankFrog(Vector2 pos, CharacterConfig config) { return createFrog(tankFrogAnimatedStateSprite, pos, config); }
    public Entity createWizardFrog(Vector2 pos, CharacterConfig config) {
        var entity = createFrog(wizardFrogAnimatedStateSprite, pos, config);

        entity.put(ColliderComponent.class, new ColliderComponent(
            List.of(
                new ThrottledCollisionActionEntry(
                    config.atk_speed(),
                    new BasicColliderActionEntry(
                        new RectangleShape(config.attackRange(), TILE_HEIGHT / 2, 0.f, TILE_HEIGHT / 4),
                        new FrogAttackCollisionAction(
                            vector2 -> BulletFactory.get().createFireball(
                                vector2.add(config.bulletConfig().offsetX(), config.bulletConfig().offsetY()),
                                config.bulletConfig().speed()
                            )
                        ),
                        ColliderType.ACTION,
                        new BasicCollisionActionFilter(EntityType.HEDGEHOG, ColliderType.ENTITY)
                        ))
            )
        ));
        return entity;
    }

    @Override
    public void dispose() {
        Logger.trace("Disposing FrogFactory");
        basicFrogTexture.dispose();
        knightFrogTexture.dispose();
        moneyFrogTexture.dispose();
        tankFrogTexture.dispose();
        wizardFrogTexture.dispose();
    }


    private Entity createFrog(AnimatedStateSprite<FrogState> animatedStateSprite, Vector2 pos, CharacterConfig config) {
        Logger.trace("Creating Frog Entity in factory");
        Entity entity = new Entity(EntityType.FROG);
        WorldTransformComponent transform = new WorldTransformComponent(pos, new Vector2(config.speed(), 0));
        StateMachine<FrogState> generalStateMachine = new StateMachine<>(FrogState.IDLE);
        generalStateMachine.addState(FrogState.IDLE, FrogState.IDLE);
        generalStateMachine.addState(FrogState.ACTION, FrogState.IDLE);
        generalStateMachine.addState(FrogState.DYING, FrogState.NONEXISTENT, entity.getMarkForRemovalRunnable());
        generalStateMachine.addState(FrogState.NONEXISTENT, FrogState.NONEXISTENT);
        AliveEntityStateComponent<FrogState> state = new AliveEntityStateComponent<>(generalStateMachine);
        AliveEntityRenderableComponent<FrogState> renderable =
            new AliveEntityRenderableComponent<>(
                transform,
                state,
                animatedStateSprite);
        entity
            .put(TransformComponent.class, transform)
            .put(StateComponent.class, state)
            .put(RenderableComponent.class, renderable);
        return entity;
    }

    private AnimatedStateSprite<FrogState> createAnimatedStateSprite(Texture texture) {
        Map<FrogState, AnimatedSprite> animatedSprites = new HashMap<>();

        animatedSprites.put(FrogState.IDLE, animationFactory.Animation(texture, 0, 0, 7));
        animatedSprites.put(FrogState.ACTION, animationFactory.Animation(texture, 2, 0, 5));
        animatedSprites.put(FrogState.DYING, animationFactory.Animation(texture, 4, 0, 8));
        animatedSprites.put(FrogState.NONEXISTENT, AnimatedSprite.empty());
        return new AnimatedStateSprite<>(animatedSprites);
    }
}
