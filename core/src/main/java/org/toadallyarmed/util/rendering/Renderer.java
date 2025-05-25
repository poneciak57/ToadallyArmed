package org.toadallyarmed.util.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class Renderer implements Disposable {
    private final SpriteBatch spriteBatch;
    private final Color defaultColor;

    public Renderer() {
        this.spriteBatch = new SpriteBatch();
        this.defaultColor = Color.WHITE;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
