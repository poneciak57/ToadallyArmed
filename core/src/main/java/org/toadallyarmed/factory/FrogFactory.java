package org.toadallyarmed.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import org.toadallyarmed.component.WorldTransformComponent;
import org.toadallyarmed.component.frog.AnimatedFrogRenderableComponent;
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
import org.toadallyarmed.util.rendering.Sprite;
import org.toadallyarmed.util.logger.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FrogFactory implements Disposable {
    private final Texture basicFrogTexture, knightFrogTexture, moneyFrogTexture, tankFrogTexture, wizardFrogTexture;
    private final TextureRegion basicFrogTextureRegion, knightFrogTextureRegion, moneyFrogTextureRegion, tankFrogTextureRegion, wizardFrogTextureRegion;

    private final Sprite basicFrogSprite, knightFrogSprite, moneyFrogSprite, tankFrogSprite, wizardFrogSprite;
    AnimatedStateSprite<FrogState> basicFrogAnimatedStateSprite;
    private static final FrogFactory frogFactory = new FrogFactory();
    private FrogFactory() {
        Logger.trace("Initializing FrogFactory");
        basicFrogTexture= new Texture("GameScreen/Frogs/basicFrog.png");
        basicFrogTextureRegion = new TextureRegion(basicFrogTexture, 0, 0, 44, 33);
        basicFrogSprite = new Sprite(
            basicFrogTextureRegion,
            new Vector2( -0.3F, 0.1F),
            new Vector2(1.5F, 1.5F));
        knightFrogTexture= new Texture("GameScreen/Frogs/knightFrog.png");
        knightFrogTextureRegion = new TextureRegion(knightFrogTexture, 0, 0, 44, 33);
        knightFrogSprite = new Sprite(
            knightFrogTextureRegion,
            new Vector2( -0.3F, 0.1F),
            new Vector2(1.5F, 1.5F));
        moneyFrogTexture= new Texture("GameScreen/Frogs/moneyFrog.png");
        moneyFrogTextureRegion = new TextureRegion(moneyFrogTexture, 0, 0, 44, 33);
        moneyFrogSprite = new Sprite(
            moneyFrogTextureRegion,
            new Vector2( -0.3F, 0.1F),
            new Vector2(1.5F, 1.5F));
        tankFrogTexture= new Texture("GameScreen/Frogs/tankFrog.png");
        tankFrogTextureRegion = new TextureRegion(tankFrogTexture, 0, 0, 44, 33);
        tankFrogSprite = new Sprite(
            tankFrogTextureRegion,
            new Vector2( -0.3F, 0.1F),
            new Vector2(1.5F, 1.5F));
        wizardFrogTexture= new Texture("GameScreen/Frogs/wizardFrog.png");
        wizardFrogTextureRegion = new TextureRegion(wizardFrogTexture, 0, 0, 44, 33);
        wizardFrogSprite = new Sprite(
            wizardFrogTextureRegion,
            new Vector2( -0.3F, 0.1F),
            new Vector2(1.5F, 1.5F));

        setupAnimations();

        Logger.debug("Initialized FrogFactory successfully");
    }

    public static FrogFactory get() {
        return frogFactory;
    }

    private void setupAnimations() {
        TextureRegion[][] tmp = TextureRegion.split(basicFrogTexture,
            basicFrogTexture.getWidth() / 9,
            basicFrogTexture.getHeight() / 5);

        Map<FrogState, AnimatedSprite> basicFrogAnimations = new HashMap<>();

        TextureRegion[] frames = Arrays.copyOfRange(tmp[0], 0, 7);
        Animation<TextureRegion> animation = new Animation<>(0.08f, frames);
        basicFrogAnimations.put(FrogState.IDLE, new AnimatedSprite(
            animation,
            new Vector2(-0.4f, -0.4f),
            new Vector2(2F, 2F)
        ));

        frames = Arrays.copyOfRange(tmp[2], 0, 5);
        animation = new Animation<>(0.08f, frames);
        basicFrogAnimations.put(FrogState.ACTION, new AnimatedSprite(
            animation,
            new Vector2(-0.4f, -0.4f),
            new Vector2(2F, 2F)
        ));

        frames = Arrays.copyOfRange(tmp[4], 0, 8);
        animation = new Animation<>(0.08f, frames);
        basicFrogAnimations.put(FrogState.DYING, new AnimatedSprite(
            animation,
            new Vector2(-0.4f, -0.4f),
            new Vector2(2F, 2F)
        ));

        basicFrogAnimations.put(FrogState.NONEXISTENT, AnimatedSprite.empty());

        basicFrogAnimatedStateSprite = new AnimatedStateSprite<>(basicFrogAnimations);
    }

    public Entity createBasicFrog() {
        Logger.trace("Creating Frog Entity in factory");
        WorldTransformComponent transform = new WorldTransformComponent();
        FrogStateComponent frogState = new FrogStateComponent();
        AnimatedFrogRenderableComponent renderable =
            new AnimatedFrogRenderableComponent(
                transform,
                frogState,
                basicFrogAnimatedStateSprite);
        return new Entity.EntityBuilder(EntityType.FROG)
            .add(TransformComponent.class, transform)
            .add(StateComponent.class, frogState)
            .add(RenderableComponent.class, renderable)
            .build();
    }
    public Entity createKnightFrog() {
        Logger.trace("Creating Frog Entity in factory");
        WorldTransformComponent transform = new WorldTransformComponent();
        FrogRenderableComponent renderable = new FrogRenderableComponent(transform, knightFrogSprite);
        return new Entity.EntityBuilder(EntityType.FROG)
            .add(TransformComponent.class, transform)
            .add(RenderableComponent.class, renderable)
            .build();
    }
    public Entity createMoneyFrog() {
        Logger.trace("Creating Frog Entity in factory");
        WorldTransformComponent transform = new WorldTransformComponent();
        FrogRenderableComponent renderable = new FrogRenderableComponent(transform, moneyFrogSprite);
        return new Entity.EntityBuilder(EntityType.FROG)
            .add(TransformComponent.class, transform)
            .add(RenderableComponent.class, renderable)
            .build();
    }
    public Entity createTankFrog() {
        Logger.trace("Creating Frog Entity in factory");
        WorldTransformComponent transform = new WorldTransformComponent();
        FrogRenderableComponent renderable = new FrogRenderableComponent(transform, tankFrogSprite);
        return new Entity.EntityBuilder(EntityType.FROG)
            .add(TransformComponent.class, transform)
            .add(RenderableComponent.class, renderable)
            .build();
    }
    public Entity createWizardFrog() {
        Logger.trace("Creating Frog Entity in factory");
        WorldTransformComponent transform = new WorldTransformComponent();
        FrogRenderableComponent renderable = new FrogRenderableComponent(transform, wizardFrogSprite);
        return new Entity.EntityBuilder(EntityType.FROG)
            .add(TransformComponent.class, transform)
            .add(RenderableComponent.class, renderable)
            .build();
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
}
