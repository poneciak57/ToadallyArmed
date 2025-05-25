package org.toadallyarmed.util.rendering;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class Renderer implements Disposable {
    final SpriteBatch spriteBatch;
    final protected Color DEFAULT_COLOR = Color.WHITE;

    public Renderer() {
        this.spriteBatch = new SpriteBatch();
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
