package org.toadallyarmed.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class Renderer implements Disposable {
    private SpriteBatch spriteBatch;

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }
}
