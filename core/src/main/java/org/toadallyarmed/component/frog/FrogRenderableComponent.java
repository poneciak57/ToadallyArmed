package org.toadallyarmed.component.frog;

import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.util.Renderer;
import org.toadallyarmed.util.Sprite;

public class FrogRenderableComponent implements RenderableComponent {
    final TransformComponent transform;
    final Sprite sprite;

    public FrogRenderableComponent(TransformComponent transform, Sprite sprite) {
        this.transform = transform;
        this.sprite = sprite;
    }

    @Override
    public void render(Renderer renderer) {
        sprite.render(renderer, transform.getPosition());
    }
}
