package org.toadallyarmed.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import org.toadallyarmed.component.WorldTransformComponent;
import org.toadallyarmed.component.hedgehog.HedgehogRenderableComponent;
import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.entity.EntityType;
import org.toadallyarmed.util.Sprite;
import org.toadallyarmed.util.logger.Logger;

public class HedgehogFactory implements Disposable {
    private Texture basicHedgehogTexture, fastHedgehogTexture, strongHedgehogTexture, healthyHedgehogTexture;
    private TextureRegion basicHedgehogTextureRegion, fastHedgehogTextureRegion, strongHedgehogTextureRegion, healthyHedgehogTextureRegion;

    private Sprite basicHedgehogSprite, fastHedgehogSprite, strongHedgehogSprite, healthyHedgehogSprite;
    private static final HedgehogFactory frogFactory = new HedgehogFactory();
    private HedgehogFactory() {
        Logger.trace("Initializing FrogFactory");
        basicHedgehogTexture= new Texture("GameScreen/Hedgehogs/basicHedgehog.png");
        basicHedgehogTextureRegion = new TextureRegion(basicHedgehogTexture, 120, 80, 24, 16);
        basicHedgehogSprite = new Sprite(
            basicHedgehogTextureRegion,
            new Vector2( 0, 0.1F),
            new Vector2(0.9F, 0.9F));
        fastHedgehogTexture= new Texture("GameScreen/Hedgehogs/fastHedgehog.png");
        fastHedgehogTextureRegion = new TextureRegion(fastHedgehogTexture, 120, 80, 24, 16);
        fastHedgehogSprite = new Sprite(
            fastHedgehogTextureRegion,
            new Vector2( 0, 0.1F),
            new Vector2(0.9F, 0.9F));
        strongHedgehogTexture= new Texture("GameScreen/Hedgehogs/strongHedgehog.png");
        strongHedgehogTextureRegion = new TextureRegion(strongHedgehogTexture, 120, 80, 24, 16);
        strongHedgehogSprite = new Sprite(
            strongHedgehogTextureRegion,
            new Vector2( 0, 0.1F),
            new Vector2(0.9F, 0.9F));
        healthyHedgehogTexture= new Texture("GameScreen/Hedgehogs/healthyHedgehog.png");
        healthyHedgehogTextureRegion = new TextureRegion(healthyHedgehogTexture, 120, 80, 24, 16);
        healthyHedgehogSprite = new Sprite(
            healthyHedgehogTextureRegion,
            new Vector2( 0, 0.1F),
            new Vector2(0.9F, 0.9F));
        Logger.debug("Initialized HedgehogFactory successfully");
    }

    public static HedgehogFactory get() {
        return frogFactory;
    }

    public Entity createBasicHedgehog() {
        Logger.trace("Creating Hedgehog Entity in factory");
        WorldTransformComponent transform = new WorldTransformComponent();
        HedgehogRenderableComponent renderable = new HedgehogRenderableComponent(transform, basicHedgehogSprite);
        return new Entity.EntityBuilder(EntityType.HEDGEHOG)
            .add(TransformComponent.class, transform)
            .add(RenderableComponent.class, renderable)
            .build();
    }
    public Entity createFastHedgehog() {
        Logger.trace("Creating Hedgehog Entity in factory");
        WorldTransformComponent transform = new WorldTransformComponent();
        HedgehogRenderableComponent renderable = new HedgehogRenderableComponent(transform, fastHedgehogSprite);
        return new Entity.EntityBuilder(EntityType.HEDGEHOG)
            .add(TransformComponent.class, transform)
            .add(RenderableComponent.class, renderable)
            .build();
    }
    public Entity createStrongHedgehog() {
        Logger.trace("Creating Hedgehog Entity in factory");
        WorldTransformComponent transform = new WorldTransformComponent();
        HedgehogRenderableComponent renderable = new HedgehogRenderableComponent(transform, strongHedgehogSprite);
        return new Entity.EntityBuilder(EntityType.HEDGEHOG)
            .add(TransformComponent.class, transform)
            .add(RenderableComponent.class, renderable)
            .build();
    }
    public Entity createHealthyHedgehog() {
        Logger.trace("Creating Hedgehog Entity in factory");
        WorldTransformComponent transform = new WorldTransformComponent();
        HedgehogRenderableComponent renderable = new HedgehogRenderableComponent(transform, healthyHedgehogSprite);
        return new Entity.EntityBuilder(EntityType.HEDGEHOG)
            .add(TransformComponent.class, transform)
            .add(RenderableComponent.class, renderable)
            .build();
    }

    @Override
    public void dispose() {
        Logger.trace("Disposing HedgehogFactory");
        basicHedgehogTexture.dispose();
        fastHedgehogTexture.dispose();
        strongHedgehogTexture.dispose();
        healthyHedgehogTexture.dispose();
    }
}
