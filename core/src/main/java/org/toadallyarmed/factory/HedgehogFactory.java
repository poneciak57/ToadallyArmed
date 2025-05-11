package org.toadallyarmed.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import org.toadallyarmed.component.HealthComponent;
import org.toadallyarmed.component.WorldTransformComponent;
import org.toadallyarmed.component.hedgehog.HedgehogRenderableComponent;
import org.toadallyarmed.component.hedgehog.HedgehogState;
import org.toadallyarmed.component.hedgehog.HedgehogStateComponent;
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

public class HedgehogFactory implements Disposable {
    private static final HedgehogFactory factoryInstance = new HedgehogFactory();

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
        WorldTransformComponent transform = new WorldTransformComponent(pos, new Vector2(config.speed(), 0.f));
        HealthComponent health = new HealthComponent(config.hp());
        HedgehogStateComponent hedgehogState = new HedgehogStateComponent();
        HedgehogRenderableComponent renderable =
            new HedgehogRenderableComponent(
                transform,
                hedgehogState,
                animatedStateSprite);
        return new Entity.EntityBuilder(EntityType.HEDGEHOG)
            .add(TransformComponent.class, transform)
            .add(StateComponent.class, hedgehogState)
            .add(RenderableComponent.class, renderable)
            .add(HealthComponent.class, health)
            .build();
    }

    @SuppressWarnings("ReassignedVariable")
    private AnimatedStateSprite<HedgehogState> createAnimatedStateSprite(Texture texture) {
        final float FRAME_DURATION          = 0.12f;
        final Vector2 OFFSET                = new Vector2(0, 0.1F);
        final Vector2 BASE_DIMENSIONS       = new Vector2(0.9F, 0.9F);

        Map<HedgehogState, AnimatedSprite> animatedSprites = new HashMap<>();
        TextureRegion[][] framesGrid;
        TextureRegion[] frames;
        Animation<TextureRegion> animation;

        framesGrid = TextureRegion.split(texture,
            texture.getWidth() / 6,
            texture.getHeight() / 4);

        frames = Arrays.copyOfRange(framesGrid[0], 4, 5);
        reverseInPlace(frames);
        animation = new Animation<>(FRAME_DURATION, frames);
        animatedSprites.put(HedgehogState.IDLE, new AnimatedSprite(animation, OFFSET, BASE_DIMENSIONS));

        frames = Arrays.copyOfRange(framesGrid[1], 0, 5);
        reverseInPlace(frames);
        animation = new Animation<>(FRAME_DURATION, frames);
        animatedSprites.put(HedgehogState.WALKING, new AnimatedSprite(animation, OFFSET, BASE_DIMENSIONS));

        frames = Arrays.copyOfRange(framesGrid[3], 1, 5);
        reverseInPlace(frames);
        animation = new Animation<>(FRAME_DURATION, frames);
        animatedSprites.put(HedgehogState.ACTION, new AnimatedSprite(animation, OFFSET, BASE_DIMENSIONS));

        frames = Arrays.copyOfRange(framesGrid[2], 2, 5);
        reverseInPlace(frames);
        animation = new Animation<>(FRAME_DURATION, frames);
        animatedSprites.put(HedgehogState.DYING, new AnimatedSprite(animation, OFFSET, BASE_DIMENSIONS));

        animatedSprites.put(HedgehogState.NONEXISTENT, AnimatedSprite.empty());

        return new AnimatedStateSprite<>(animatedSprites);
    }

    private void reverseInPlace(TextureRegion[] array) {
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            TextureRegion temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
        }
    }
}
