package org.toadallyarmed.util.rendering;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SimpleTextureRenderer implements TextureRenderer {
    Renderer renderer;

    public SimpleTextureRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void draw(TextureRegion region, float x, float y, float width, float height) {
        SpriteBatch spriteBatch = renderer.getSpriteBatch();
        spriteBatch.draw(region, x, y, width, height);
    }
}
