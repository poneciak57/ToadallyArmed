package org.toadallyarmed.component.hedgehog;

import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.util.Renderer;
import org.toadallyarmed.util.Sprite;

public class HedgehogRenderableComponent implements RenderableComponent {
    final TransformComponent transform;
    final Sprite sprite;

    public HedgehogRenderableComponent(TransformComponent transform, Sprite sprite) {
        this.transform = transform;
        this.sprite = sprite;
    }

    @Override
    public void render(Renderer renderer) {
        sprite.render(renderer, transform.getPosition());
    }
}
