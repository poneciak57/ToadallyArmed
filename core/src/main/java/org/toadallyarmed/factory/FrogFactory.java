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

    public Entity createBasicFrog() { return createFrog(basicFrogAnimatedStateSprite); }
    public Entity createKnightFrog() { return createFrog(knightFrogAnimatedStateSprite); }
    public Entity createMoneyFrog() { return createFrog(moneyFrogAnimatedStateSprite); }
    public Entity createTankFrog() { return createFrog(tankFrogAnimatedStateSprite); }
    public Entity createWizardFrog() { return createFrog(wizardFrogAnimatedStateSprite); }

    @Override
    public void dispose() {
        Logger.trace("Disposing FrogFactory");
        basicFrogTexture.dispose();
        knightFrogTexture.dispose();
        moneyFrogTexture.dispose();
        tankFrogTexture.dispose();
        wizardFrogTexture.dispose();
    }


    private Entity createFrog(AnimatedStateSprite<FrogState> animatedStateSprite) {
        Logger.trace("Creating Frog Entity in factory");
        WorldTransformComponent transform = new WorldTransformComponent();
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
        final float FRAME_DURATION          = 0.08f;
        final Vector2 OFFSET                = new Vector2(-0.4f, -0.53f);
        final Vector2 BASE_DIMENSIONS       = new Vector2(2F, 2F);

        Map<FrogState, AnimatedSprite> animatedSprites = new HashMap<>();
        TextureRegion[][] framesGrid;
        TextureRegion[] frames;
        Animation<TextureRegion> animation;

        framesGrid = TextureRegion.split(texture,
            texture.getWidth() / 9,
            texture.getHeight() / 5);

        frames = Arrays.copyOfRange(framesGrid[0], 0, 7);
        animation = new Animation<>(FRAME_DURATION, frames);
        animatedSprites.put(FrogState.IDLE, new AnimatedSprite(animation, OFFSET, BASE_DIMENSIONS));

        frames = Arrays.copyOfRange(framesGrid[2], 0, 5);
        animation = new Animation<>(FRAME_DURATION, frames);
        animatedSprites.put(FrogState.ACTION, new AnimatedSprite(animation, OFFSET, BASE_DIMENSIONS));

        frames = Arrays.copyOfRange(framesGrid[4], 0, 8);
        animation = new Animation<>(FRAME_DURATION, frames);
        animatedSprites.put(FrogState.DYING, new AnimatedSprite(animation, OFFSET, BASE_DIMENSIONS));

        animatedSprites.put(FrogState.NONEXISTENT, AnimatedSprite.empty());

        return new AnimatedStateSprite<>(animatedSprites);
    }
}
