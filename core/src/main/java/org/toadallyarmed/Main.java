package org.toadallyarmed;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.toadallyarmed.gameplay.GameScreen;
import org.toadallyarmed.util.logger.LogLevel;
import org.toadallyarmed.util.logger.Logger;
import org.toadallyarmed.util.logger.TerminalLoggerBackend;

import java.util.List;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public SpriteBatch spriteBatch;
    public BitmapFont font;

    public void updateFontScale(Viewport viewport) {
        //font has 15pt, but we need to scale it to our viewport by ratio of viewport height to screen height
        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
    }

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        Logger.init(List.of(new TerminalLoggerBackend(LogLevel.DEBUG)), LogLevel.DEBUG);
        setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        font.dispose();
    }
}
