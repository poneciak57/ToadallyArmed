package org.toadallyarmed.util.rendering;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.util.StateMachine;

public class AnimatedStateMachineSpriteInstance<State extends Enum<State>> {
    private final AnimatedStateSprite<State> animatedStateSprite;
    private final EffectController<State> effectController;

    public AnimatedStateMachineSpriteInstance(
        AnimatedStateSprite<State> animatedStateSprite,
        StateMachine<State> stateMachine) {
        this.animatedStateSprite = animatedStateSprite;
        this.effectController = new EffectController<>(stateMachine, animatedStateSprite);
    }

    public void render(TextureRenderer textureRenderer, Vector2 position, float deltaTime) {
        final var timedState = effectController.performEffect(deltaTime);
        animatedStateSprite.render(textureRenderer, position, timedState.state(), timedState.elapsedTime());
    }
}
