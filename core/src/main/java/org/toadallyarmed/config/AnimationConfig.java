package org.toadallyarmed.config;

import com.badlogic.gdx.math.Vector2;

public record AnimationConfig(
    float FRAME_DURATION,
    Vector2 OFFSET,
    Vector2 BASE_DIMENSIONS,
    int width,
    int height,
    boolean reversed
) {}
