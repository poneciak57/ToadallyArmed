package org.toadallyarmed.component.coin;

import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.util.Renderer;
import org.toadallyarmed.util.Sprite;

public class CoinRenderableComponent implements RenderableComponent {
    final TransformComponent transform;
    final Sprite sprite;

    public CoinRenderableComponent(TransformComponent transform, Sprite sprite) {
        this.transform = transform;
        this.sprite = sprite;
    }

    @Override
    public void render(Renderer renderer, float deltaTime) {
        sprite.render(renderer, transform.getPosition());
    }
}
