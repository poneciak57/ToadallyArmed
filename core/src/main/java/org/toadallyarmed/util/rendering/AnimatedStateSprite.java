package org.toadallyarmed.util.rendering;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.util.logger.Logger;

import java.util.Map;

public class AnimatedStateSprite<State extends Enum<State>> {
    final Map<State, AnimatedSprite> animations;

    public AnimatedStateSprite(Map<State, AnimatedSprite> animations) {
        this.animations = animations;
    }

    public float getAnimationDurationForState(State state) {
        AnimatedSprite animatedSprite = getAnimatedSpriteForState(state);
        if (animatedSprite == null) { return 0f; }
        else return animatedSprite.getAnimationDuration();
    }

    public void render(TextureRenderer textureRenderer, Vector2 position, State state, float animationTimer) {
        AnimatedSprite animatedSprite = getAnimatedSpriteForState(state);
        if (animatedSprite != null) {
            animatedSprite.render(textureRenderer, position, animationTimer);
        }
    }

    private AnimatedSprite getAnimatedSpriteForState(State state) {
        AnimatedSprite animatedSprite = animations.get(state);
        if (animatedSprite == null) {
            Logger.error("AnimationSprite not supplied for state " + state);
            return null;
        } else {
            return animatedSprite;
        }
    }
}
