package org.toadallyarmed.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Sprite {
    TextureRegion textureRegion;

    public void draw(Renderer renderer, float x, float y) {
        SpriteBatch batch = renderer.getSpriteBatch();
        batch.draw(textureRegion, x, y, 1, 1);
    }
}
