package org.toadallyarmed.util.rendering;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.util.logger.Logger;

import java.util.Map;

public class AnimatedStateSprite<State extends Enum<State>> {
    Map<State, AnimatedSprite> animations;

    public AnimatedStateSprite(Map<State, AnimatedSprite> animations) {
        this.animations = animations;
    }

    public float getAnimationDurationForState(State state) {
        AnimatedSprite animatedSprite = getAnimatedSpriteForState(state);
        if (animatedSprite == null) { return 0f; }
        else return animatedSprite.getAnimationDuration();
    }

    public void render(Renderer renderer, Vector2 position, State state, float animationTimer) {
        AnimatedSprite animatedSprite = getAnimatedSpriteForState(state);
        if (animatedSprite != null) {
            animatedSprite.render(renderer, position, animationTimer);
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
