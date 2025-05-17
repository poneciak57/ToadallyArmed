package org.toadallyarmed.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import org.toadallyarmed.component.WorldTransformComponent;
import org.toadallyarmed.component.frog.FrogRenderableComponent;
import org.toadallyarmed.component.frog.FrogState;
import org.toadallyarmed.component.frog.FrogStateComponent;
import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.config.CharacterConfig;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.entity.EntityType;
import org.toadallyarmed.util.rendering.AnimatedSprite;
import org.toadallyarmed.util.rendering.AnimatedStateSprite;
import org.toadallyarmed.util.logger.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FrogFactory implements Disposable {
    private static final FrogFactory factoryInstance = new FrogFactory();
    private final FrogAnimationFactory animationFactory = new FrogAnimationFactory();

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
    public Entity createMoneyFrog(Vector2 pos, CharacterConfig config) { return createFrog(moneyFrogAnimatedStateSprite, pos, config); }
    public Entity createTankFrog(Vector2 pos, CharacterConfig config) { return createFrog(tankFrogAnimatedStateSprite, pos, config); }
    public Entity createWizardFrog(Vector2 pos, CharacterConfig config) { return createFrog(wizardFrogAnimatedStateSprite, pos, config); }

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
        WorldTransformComponent transform = new WorldTransformComponent(pos, new Vector2(config.speed(), 0));
        FrogStateComponent frogState = new FrogStateComponent();
        FrogRenderableComponent renderable =
            new FrogRenderableComponent(
                transform,
                frogState,
                animatedStateSprite);
        return new Entity.EntityBuilder(EntityType.FROG)
            .add(TransformComponent.class, transform)
            .add(StateComponent.class, frogState)
            .add(RenderableComponent.class, renderable)
            .build();
    }

    @SuppressWarnings("ReassignedVariable")
    private AnimatedStateSprite<FrogState> createAnimatedStateSprite(Texture texture) {
        Map<FrogState, AnimatedSprite> animatedSprites = new HashMap<>();

        animatedSprites.put(FrogState.IDLE, animationFactory.Animation(texture, 0, 0, 7));
        animatedSprites.put(FrogState.ACTION, animationFactory.Animation(texture, 2, 0, 5));
        animatedSprites.put(FrogState.DYING, animationFactory.Animation(texture, 4, 0, 8));
        animatedSprites.put(FrogState.NONEXISTENT, AnimatedSprite.empty());
        return new AnimatedStateSprite<>(animatedSprites);
    }
}
