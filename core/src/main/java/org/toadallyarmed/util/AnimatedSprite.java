package org.toadallyarmed.util;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class AnimatedSprite {
    Animation<TextureRegion> animation;
    Vector2 offsetPosition = new Vector2(0f, 0f);
    Vector2 baseDimensions = new Vector2(1f, 1f);

    public AnimatedSprite(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    public AnimatedSprite(Animation<TextureRegion> animation, Vector2 offsetPosition, Vector2 baseDimensions) {
        this(animation);
        this.offsetPosition.set(offsetPosition);
        this.baseDimensions.set(baseDimensions);
    }

    public void render(Renderer renderer, Vector2 position, float animationTimer) {
        SpriteBatch batch = renderer.getSpriteBatch();
        TextureRegion currentFrame = animation.getKeyFrame(animationTimer, true);
        batch.draw(currentFrame,
            offsetPosition.x+position.x,
            offsetPosition.y+position.y,
            baseDimensions.x,
            baseDimensions.y);
    }

    public float getAnimationDuration() {
        return animation.getAnimationDuration();
    }
}
