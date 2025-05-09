package org.toadallyarmed.component.frog;

import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.util.Renderer;
import org.toadallyarmed.util.Sprite;

public class FrogRenderableComponent implements RenderableComponent {
    final TransformComponent transform;
    final FrogStateComponent frogState;
    final Sprite sprite;

    public FrogRenderableComponent(TransformComponent transform, FrogStateComponent frogState, Sprite sprite) {
        this.transform = transform;
        this.frogState = frogState;
        this.sprite = sprite;
    }

    @Override
    public void render(Renderer renderer) {
        sprite.render(renderer, transform.getPosition());
    }
}
