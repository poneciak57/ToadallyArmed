package org.toadallyarmed.component.frog;

import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.util.AnimatedSprite;
import org.toadallyarmed.util.Renderer;
import org.toadallyarmed.util.logger.Logger;

import java.util.Map;

// WARNING: This is temporary class. Its functionality should be moved to FrogRenderableComponent!
public class AnimatedFrogRenderableComponent implements RenderableComponent {
    final TransformComponent transformComponent;
    final FrogStateComponent fullStateComponent;

    final Map<FrogState, AnimatedSprite> animations;

    FrogState prevGeneralState;
    float stateElapsedTime = 0f;

    public AnimatedFrogRenderableComponent(
        TransformComponent transformComponent,
        FrogStateComponent fullStateComponent,
        Map<FrogState, AnimatedSprite> animations) {
        this.transformComponent = transformComponent;
        this.fullStateComponent = fullStateComponent;
        this.animations = animations;

        prevGeneralState = fullStateComponent.getGeneralState();
    }

    FrogState getAndUpdateState() {
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
        FrogState state = getAndUpdateState();
        AnimatedSprite animatedSprite = getAnimatedSpriteForState(state);
        if (animatedSprite != null) {
            animatedSprite.render(renderer, transformComponent.getPosition(), stateElapsedTime);
        }
    }

    AnimatedSprite getAnimatedSpriteForState(FrogState state) {
        AnimatedSprite animatedSprite = animations.get(state);
        if (animatedSprite == null) {
            Logger.error("AnimationSprite not supplied for state " + state);
            return null;
        } else {
            return animatedSprite;
        }
    }

    float getAnimationDurationForState(FrogState state) {
        AnimatedSprite animatedSprite = getAnimatedSpriteForState(state);
        if (animatedSprite == null) { return 0f; }
        else return animatedSprite.getAnimationDuration();
    }
}
