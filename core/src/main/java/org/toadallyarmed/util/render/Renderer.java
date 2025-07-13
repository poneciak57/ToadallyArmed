package org.toadallyarmed.util.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import org.toadallyarmed.util.log.Logger;

public class Renderer implements Disposable {
    private final SpriteBatch spriteBatch;
    private final ShaderProgram defaultShader;
    private ShaderProgram aliveEntityShader;

    public Renderer() {
        this.spriteBatch = new SpriteBatch();
        this.defaultShader = spriteBatch.getShader();

        setupShaders();
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public ShaderProgram getDefaultShader() {
        return defaultShader;
    }

    public ShaderProgram getAliveEntityShader() {
        return aliveEntityShader;
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        aliveEntityShader.dispose();
    }

    private void setupShaders() {
        aliveEntityShader = new ShaderProgram(
            Gdx.files.internal("assets/AliveEntity/AliveEntity.vert"),
            Gdx.files.internal("assets/AliveEntity/AliveEntity.frag")
        );
        if (!aliveEntityShader.isCompiled()) {
            Logger.error("Could not compile fragment shader: " + aliveEntityShader.getLog());
        }
    }
}
