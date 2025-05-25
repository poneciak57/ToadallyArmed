package org.toadallyarmed.util.rendering.effect.hurt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import org.toadallyarmed.util.logger.Logger;
import org.toadallyarmed.util.rendering.Renderer;
import org.toadallyarmed.util.rendering.TextureRenderer;

public class HurtEffectTextureRenderer implements TextureRenderer {
    static final Color DESTINATION_COLOR = Color.RED;

    final Renderer renderer;
    final Color color;

    /**
     * @param ratio Value between 0f and 1f determining how much has the hurt effect advanced.
     */
    public HurtEffectTextureRenderer(Renderer renderer, float ratio) {
        Logger.errorIfNot(0f <= ratio && ratio <= 1f, "HurtEffectTextureRenderer: ratio should be between 0.0f and 1.0f.");
        this.renderer = renderer;

        final Color start = renderer.getDefaultColor();
        final Color end = DESTINATION_COLOR;

        color = new Color(
            Interpolation.linear.apply(start.r, end.r, ratio),
            Interpolation.linear.apply(start.g, end.g, ratio),
            Interpolation.linear.apply(start.b, end.b, ratio),
            Interpolation.linear.apply(start.a, end.a, ratio)
        );
    }

    @Override
    public void draw(TextureRegion region, float x, float y, float width, float height) {
        SpriteBatch spriteBatch = renderer.getSpriteBatch();
        spriteBatch.setColor(color);
        spriteBatch.draw(region, x, y, width, height);
        spriteBatch.setColor(renderer.getDefaultColor());
    }
}
