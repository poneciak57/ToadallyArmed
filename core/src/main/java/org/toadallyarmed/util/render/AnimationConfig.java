package org.toadallyarmed.util.render;

import com.badlogic.gdx.math.Vector2;

public record AnimationConfig(
    float frameDuration,
    Vector2 offset,
    Vector2 baseDimensions,
    int width,
    int height,
    boolean reversed
) {}
