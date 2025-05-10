package org.toadallyarmed.component.coin;

import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.util.AnimatedSprite;
import org.toadallyarmed.util.Renderer;
import org.toadallyarmed.util.logger.Logger;

import java.util.Map;

// WARNING: This is temporary class. Its functionality should be moved to CoinRenderableComponent!
public class AnimatedCoinRenderableComponent implements RenderableComponent {
    final TransformComponent transformComponent;
    final CoinStateComponent fullStateComponent;

    final Map<CoinState, AnimatedSprite> animations;

    CoinState prevGeneralState;
    float stateElapsedTime = 0f;

    public AnimatedCoinRenderableComponent(
        TransformComponent transformComponent,
        CoinStateComponent fullStateComponent,
        Map<CoinState, AnimatedSprite> animations) {
        this.transformComponent = transformComponent;
        this.fullStateComponent = fullStateComponent;
        this.animations = animations;

        prevGeneralState = fullStateComponent.getGeneralState();
    }

    CoinState getAndUpdateState() {
        if (stateElapsedTime >= getAnimationDurationForState(prevGeneralState)) {
            stateElapsedTime = 0f;
            fullStateComponent.advanceState();
            prevGeneralState = fullStateComponent.getGeneralState();
        }
        return prevGeneralState;
    }

    @Override
    public void render(Renderer renderer, float deltaTime) {
        stateElapsedTime += deltaTime;
        CoinState state = getAndUpdateState();
        AnimatedSprite animatedSprite = getAnimatedSpriteForState(state);
        if (animatedSprite != null) {
            animatedSprite.render(renderer, transformComponent.getPosition(), stateElapsedTime);
        }
    }

    AnimatedSprite getAnimatedSpriteForState(CoinState state) {
        AnimatedSprite animatedSprite = animations.get(state);
        if (animatedSprite == null) {
            Logger.error("AnimationSprite not supplied for state " + state);
            return null;
        } else {
            return animatedSprite;
        }
    }

    float getAnimationDurationForState(CoinState state) {
        AnimatedSprite animatedSprite = getAnimatedSpriteForState(state);
        if (animatedSprite == null) { return 0f; }
        else return animatedSprite.getAnimationDuration();
    }
}
