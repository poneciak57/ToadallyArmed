package org.toadallyarmed.component;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.util.Renderer;

public class SpriteComponent implements RenderableComponent {
    TextureRegion textureRegion;

    @Override
    public void render(Renderer renderer, float x, float y) {
        SpriteBatch batch = renderer.getSpriteBatch();
        batch.draw(textureRegion, x, y, 1, 1);
    }
}
