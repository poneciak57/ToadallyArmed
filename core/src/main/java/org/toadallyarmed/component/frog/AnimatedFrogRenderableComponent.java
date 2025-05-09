package org.toadallyarmed.component.frog;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.util.AnimatedSprite;
import org.toadallyarmed.util.Renderer;
import org.toadallyarmed.util.Sprite;
import org.toadallyarmed.util.logger.Logger;

import java.util.Map;

// WARNING: This is temporary class. Its functionality should be moved to FrogRenderableComponent
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
        FrogState newState = fullStateComponent.getGeneralState();
        if (newState != prevGeneralState) {
            stateElapsedTime = 0f;
            prevGeneralState = newState;
        }
        return newState;
    }

    @Override
    public void render(Renderer renderer, float deltaTime) {
        FrogState state = getAndUpdateState();
        stateElapsedTime += deltaTime;
        AnimatedSprite animatedSprite = animations.get(prevGeneralState);
        if (animatedSprite == null) {
            Logger.error("AnimationSprite not supplied for state " + state);
        } else {
            animatedSprite.render(renderer, transformComponent.getPosition(), stateElapsedTime);
        }
    }
}
