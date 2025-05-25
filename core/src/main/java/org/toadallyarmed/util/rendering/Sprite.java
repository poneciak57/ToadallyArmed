package org.toadallyarmed.util.rendering;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Sprite {
    final TextureRegion textureRegion;
    final Vector2 offsetPosition = new Vector2();
    final Vector2 baseDimensions = new Vector2();

    public Sprite(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public Sprite(TextureRegion textureRegion, Vector2 offsetPosition, Vector2 baseDimensions) {
        this(textureRegion);
        this.offsetPosition.set(offsetPosition);
        this.baseDimensions.set(baseDimensions);
    }

    public void render(TextureRenderer textureRenderer, Vector2 position) {
        textureRenderer.draw(textureRegion,
            offsetPosition.x+position.x,
            offsetPosition.y+position.y,
            baseDimensions.x,
            baseDimensions.y);
    }
}
