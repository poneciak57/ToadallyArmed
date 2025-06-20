package org.toadallyarmed.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.toadallyarmed.Main;
import org.toadallyarmed.util.logger.Logger;

public class FrogIntroScreen implements Screen {
    final Main main;
    final FitViewport viewport;

    final Texture backgroundTexture;
    Rectangle nextButton;

    public FrogIntroScreen(Main main) {
        Logger.info("Story screen");
        this.main = main;

        viewport = new FitViewport(10.66F, 6);
        this.main.updateFontScale(viewport);

        backgroundTexture = new Texture("GameScreen/frog_intro_background.jpg");
        setButtons();

        Logger.info("Created a new gameplay screen successfully");
    }
    private void setButtons(){
        nextButton =new Rectangle(4f, 1, 3, 1);
    }

    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();

        main.renderer.getSpriteBatch().setProjectionMatrix(viewport.getCamera().combined);
        main.renderer.getSpriteBatch().begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        main.renderer.getSpriteBatch().draw(backgroundTexture, 0, 0, worldWidth, worldHeight);

        if (Gdx.input.justTouched())
            main.setScreen(new LevelChoosingScreen(main));

        main.renderer.getSpriteBatch().end();
    }


    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
        viewport.update(width, height, true);
        main.updateFontScale(viewport);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        Logger.info("disposing a gameplay screen");
        // Destroy screen's assets here.
        backgroundTexture.dispose();
    }
}
