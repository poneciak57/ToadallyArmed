package org.toadallyarmed.util.rendering;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class AnimatedSprite {
    final Animation<TextureRegion> animation;
    final Vector2 offsetPosition;
    final Vector2 baseDimensions;

    private AnimatedSprite() {
        animation = null;
        offsetPosition = null;
        baseDimensions = null;
    }

    static public AnimatedSprite empty() {
        return new AnimatedSprite();
    }

    public AnimatedSprite(Animation<TextureRegion> animation) {
        this.animation = animation;
        offsetPosition = new Vector2(0f, 0f);
        baseDimensions = new Vector2(1f, 1f);
    }

    public AnimatedSprite(Animation<TextureRegion> animation, Vector2 offsetPosition, Vector2 baseDimensions) {
        this(animation);
        this.offsetPosition.set(offsetPosition);
        this.baseDimensions.set(baseDimensions);
    }

    public void render(Renderer renderer, Vector2 position, float animationTimer) {
        if (animation == null)
            return;

        SpriteBatch batch = renderer.getSpriteBatch();
        TextureRegion currentFrame = animation.getKeyFrame(animationTimer, false);
        batch.draw(currentFrame,
            offsetPosition.x+position.x,
            offsetPosition.y+position.y,
            baseDimensions.x,
            baseDimensions.y);
    }

    public float getAnimationDuration() {
        if (animation == null) return 0f;
        else return animation.getAnimationDuration();
    }
}
