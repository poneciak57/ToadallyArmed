package org.toadallyarmed.util.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Disposable;
import org.toadallyarmed.util.logger.Logger;

public class Renderer implements Disposable {
    private final SpriteBatch spriteBatch;
    private final Color defaultColor;
    private final ShaderProgram defaultShader;
    private ShaderProgram hurtEffectShader;

    public Renderer() {
        this.spriteBatch = new SpriteBatch();
        this.defaultColor = Color.WHITE;
        this.defaultShader = spriteBatch.getShader();

        setupShaders();
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public ShaderProgram getDefaultShader() {
        return defaultShader;
    }

    public ShaderProgram getHurtEffectShader() {
        return hurtEffectShader;
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        hurtEffectShader.dispose();
    }

    private void setupShaders() {
        hurtEffectShader = new ShaderProgram(
            Gdx.files.internal("GameScreen/Shaders/HurtEffect/hurtEffect.vert"),
            Gdx.files.internal("GameScreen/Shaders/HurtEffect/hurtEffect.frag")
        );
        if (!hurtEffectShader.isCompiled()) {
            Logger.error("Could not compile fragment shader: " + hurtEffectShader.getLog());
        }
    }
}
