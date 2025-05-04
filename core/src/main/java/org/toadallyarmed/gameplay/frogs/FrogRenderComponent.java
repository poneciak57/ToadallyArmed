package org.toadallyarmed.gameplay.frogs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.core.Entity;
import org.toadallyarmed.core.Renderer;
import org.toadallyarmed.core.Sprite;
import org.toadallyarmed.core.components.BoxComponent;
import org.toadallyarmed.core.components.EntityMissesComponentException;
import org.toadallyarmed.core.components.RenderComponent;
import org.toadallyarmed.core.components.TransformComponent;

public class FrogRenderComponent extends RenderComponent {
    final Entity entity;
    final Sprite sprite;

    final TransformComponent transform;
    final BoxComponent box;

    public FrogRenderComponent(Entity entity, Sprite sprite) throws EntityMissesComponentException {
        this.entity = entity;
        this.sprite = sprite;

        var transformOpt = entity.get(TransformComponent.class);
        if (transformOpt.isEmpty()) throw new EntityMissesComponentException("TransformComponent");
        transform = transformOpt.get();

        var boxOpt = entity.get(BoxComponent.class);
        if (boxOpt.isEmpty()) throw new EntityMissesComponentException("BoxComponent");
        box = boxOpt.get();
    }

    @Override
    public void render(Renderer renderer) {
        Vector2 position = transform.getPosition();
        sprite.draw(renderer, position.x, position.y);
    }
}
