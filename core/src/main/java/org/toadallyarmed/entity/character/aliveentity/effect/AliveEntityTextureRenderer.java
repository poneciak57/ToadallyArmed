package org.toadallyarmed.entity.character.aliveentity.effect;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import org.toadallyarmed.util.log.Logger;
import org.toadallyarmed.util.render.Renderer;
import org.toadallyarmed.util.render.TextureRenderer;

public class AliveEntityTextureRenderer implements TextureRenderer {
    final Renderer renderer;

    float hurtRatio = 0f;
    float fadeRatio = 0f;

    public AliveEntityTextureRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public void setHurtRatio(float hurtRatio) {
        Logger.errorIfNot(
            0f <= hurtRatio && hurtRatio <= 1f,
            "AliveEntityTextureRenderer: hurtRatio should be between 0.0f and 1.0f."
        );
        this.hurtRatio = hurtRatio;
    }
    public void setFadeRatio(float fadeRatio) {
        Logger.errorIfNot(
            0f <= fadeRatio && fadeRatio <= 1f,
            "AliveEntityTextureRenderer: fadeRatio should be between 0.0f and 1.0f."
        );
        this.fadeRatio = fadeRatio;
    }

    @Override
    public void draw(TextureRegion region, float x, float y, float width, float height) {
        SpriteBatch spriteBatch = renderer.getSpriteBatch();
        ShaderProgram shader = renderer.getAliveEntityShader();

        spriteBatch.setShader(shader);
        shader.setUniformf("hurtRatio", hurtRatio);
        shader.setUniformf("fadeRatio", fadeRatio);
        spriteBatch.draw(region, x, y, width, height);

        spriteBatch.setShader(renderer.getDefaultShader());
    }
}
