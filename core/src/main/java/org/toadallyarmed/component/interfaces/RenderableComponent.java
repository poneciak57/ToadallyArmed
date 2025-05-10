package org.toadallyarmed.component.interfaces;

import org.toadallyarmed.util.rendering.Renderer;

public interface RenderableComponent extends Component {
    void render(Renderer renderer, float deltaTime, float currentTimestamp);
}
