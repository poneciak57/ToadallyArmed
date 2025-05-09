package org.toadallyarmed.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import org.toadallyarmed.component.WorldTransformComponent;
import org.toadallyarmed.component.frog.FrogRenderableComponent;
import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.entity.EntityType;
import org.toadallyarmed.util.Sprite;
import org.toadallyarmed.util.logger.Logger;

public class FrogFactory implements Disposable {
    private Texture basicFrogTexture;
    private TextureRegion basicFrogTextureRegion;

    private Sprite basicFrogSprite;
    private static final FrogFactory frogFactory = new FrogFactory();
    private FrogFactory() {
        Logger.trace("Initializing FrogFactory");
        basicFrogTexture= new Texture("GameScreen/Frogs/basicFrog.png");
        basicFrogTextureRegion = new TextureRegion(basicFrogTexture, 0, 0, 44, 33);
        basicFrogSprite = new Sprite(
            basicFrogTextureRegion,
            new Vector2( -0.3F, 0.1F),
            new Vector2(1.5F, 1.5F));
        Logger.debug("Initialized FrogFactory successfully");
    }

    public static FrogFactory get() {
        return frogFactory;
    }

    public Entity createBasicFrog() {
        Logger.trace("Creating Frog Entity in factory");
        WorldTransformComponent transform = new WorldTransformComponent();
        FrogRenderableComponent renderable = new FrogRenderableComponent(transform, basicFrogSprite);
        return new Entity.EntityBuilder(EntityType.FROG)
            .add(TransformComponent.class, transform)
            .add(RenderableComponent.class, renderable)
            .build();
    }

    @Override
    public void dispose() {
        Logger.trace("Disposing FrogFactory");
        basicFrogTexture.dispose();
    }
}
