package org.toadallyarmed.util.rendering;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface TextureRenderer {
    void draw(TextureRegion region, float x, float y, float width, float height);
}
