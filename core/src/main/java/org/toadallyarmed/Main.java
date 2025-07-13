package org.toadallyarmed;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.toadallyarmed.scene.gameplay.GameConfig;
import org.toadallyarmed.scene.gameplay.DifficultyFactory;
import org.toadallyarmed.scene.menu.IntroScreen;
import org.toadallyarmed.scene.gameplay.LevelScreen;
import org.toadallyarmed.base.render.RenderingSystem;
import org.toadallyarmed.scene.test.Test2Screen;
import org.toadallyarmed.util.debug.Debugging;
import org.toadallyarmed.util.render.Renderer;
import org.toadallyarmed.util.log.LogLevel;
import org.toadallyarmed.util.log.Logger;
import org.toadallyarmed.util.log.TerminalLoggerBackend;

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
        Logger.init(List.of(new TerminalLoggerBackend(LogLevel.ERROR)), LogLevel.ERROR);
        renderer = new Renderer();
        font = new BitmapFont();
        renderingSystem = new RenderingSystem(renderer);
        if (Debugging.stressTestMode()) {
            setScreen(new Test2Screen(this));
        } else if (Debugging.debuggingMode()) {
            GameConfig config = DifficultyFactory.debug();
            setScreen(new LevelScreen(this, config));
        } else {
            setScreen(new IntroScreen(this));
        }
        backgroundMusic=Gdx.audio.newMusic(Gdx.files.internal("GameScreen/backgroundMusic.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.play();
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
        System.exit(0);
    }
}
