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
import org.toadallyarmed.util.Sprite;

public class FrogFactory implements Disposable {
    private Texture basicFrogTexture;
    private TextureRegion basicFrogTextureRegion;

    private Sprite basicFrogSprite;
    private static final FrogFactory frogFactory = new FrogFactory();
    private FrogFactory() {
        basicFrogTexture= new Texture("GameScreen/Frogs/basicFrog.png");
        basicFrogTextureRegion = new TextureRegion(basicFrogTexture, 0, 0, 44, 33);
        basicFrogSprite = new Sprite(
            basicFrogTextureRegion,
            new Vector2( -0.3F, 0.1F),
            new Vector2(1.5F, 1.5F));
    }

    public static FrogFactory get() {
        return frogFactory;
    }

    public Entity createBasicFrog() {
        Entity entity = new Entity();
        WorldTransformComponent transform = new WorldTransformComponent();
        FrogRenderableComponent renderable = new FrogRenderableComponent(transform, basicFrogSprite);
        entity.put(TransformComponent.class, transform);
        entity.put(RenderableComponent.class, renderable);
        return entity;
    }

    @Override
    public void dispose() {
        basicFrogTexture.dispose();
    }
}
