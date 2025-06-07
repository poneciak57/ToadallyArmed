package org.toadallyarmed.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.toadallyarmed.Main;
import org.toadallyarmed.util.logger.Logger;

public class LevelVictoryScreen implements Screen {
    final Main main;
    final FitViewport viewport;

    final Texture backgroundTexture;
    Rectangle startButtonBounds;

    public LevelVictoryScreen(Main main) {
        Logger.info("Victory screen");
        this.main = main;

        viewport = new FitViewport(10.66F, 6);
        this.main.updateFontScale(viewport);
        backgroundTexture = new Texture("GameScreen/level_victory_background.jpg");
        startButtonBounds =new Rectangle(4f, 1, 3, 1);

        Logger.info("Created a new gameplay screen successfully");
    }
    private void analyzeTouch(Vector3 touchPos){//touch position is obtained  (in terms of x, y)
        viewport.unproject(touchPos);
        if (startButtonBounds.contains(touchPos.x, touchPos.y)){
            Logger.info("Clicked");
            main.setScreen(new IntroScreen(main));
        }
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
            analyzeTouch(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

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
