package org.toadallyarmed.base.render;

import org.toadallyarmed.base.component.Component;
import org.toadallyarmed.util.render.Renderer;

public interface RenderableComponent extends Component {
    void render(Renderer renderer, float deltaTime, float currentNanoTime);
}
