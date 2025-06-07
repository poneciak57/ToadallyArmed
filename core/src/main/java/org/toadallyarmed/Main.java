package org.toadallyarmed;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.toadallyarmed.gameplay.IntroScreen;
import org.toadallyarmed.gameplay.LevelVictoryScreen;
import org.toadallyarmed.system.RenderingSystem;
import org.toadallyarmed.util.rendering.Renderer;
import org.toadallyarmed.util.logger.LogLevel;
import org.toadallyarmed.util.logger.Logger;
import org.toadallyarmed.util.logger.TerminalLoggerBackend;

import java.util.List;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public Renderer renderer;
    public BitmapFont font;
    public RenderingSystem renderingSystem;
    private Music backgroundMusic;

    public void updateFontScale(Viewport viewport) {
        //font has 15pt, but we need to scale it to our viewport by ratio of viewport height to screen height
        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
    }

    @Override
    public void create() {
        Logger.init(List.of(new TerminalLoggerBackend(LogLevel.DEBUG)), LogLevel.TRACE);
        renderer = new Renderer();
        font = new BitmapFont();
        renderingSystem = new RenderingSystem(renderer);
        setScreen(new IntroScreen(this));
        backgroundMusic=Gdx.audio.newMusic(Gdx.files.internal("GameScreen/backgroundMusic.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
        //setScreen(new LevelVictoryScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        font.dispose();
        backgroundMusic.dispose();
    }
}
