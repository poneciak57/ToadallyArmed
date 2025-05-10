package org.toadallyarmed.util.rendering;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Sprite {
    TextureRegion textureRegion;
    Vector2 offsetPosition = new Vector2();
    Vector2 baseDimensions = new Vector2();

    public Sprite(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public Sprite(TextureRegion textureRegion, Vector2 offsetPosition, Vector2 baseDimensions) {
        this(textureRegion);
        this.offsetPosition.set(offsetPosition);
        this.baseDimensions.set(baseDimensions);
    }

    public void render(Renderer renderer, Vector2 position) {
        SpriteBatch batch = renderer.getSpriteBatch();
        batch.draw(textureRegion,
            offsetPosition.x+position.x,
            offsetPosition.y+position.y,
            baseDimensions.x,
            baseDimensions.y);
    }
}
