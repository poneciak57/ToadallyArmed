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
import org.toadallyarmed.config.GameConfig;
import org.toadallyarmed.factory.DifficultyFactory;
import org.toadallyarmed.util.logger.Logger;

public class LevelChoosingScreen implements Screen {
    final Main main;
    final FitViewport viewport;

    final Texture backgroundTexture;
    Rectangle easyButtonBounds, mediumButtonBounds, hardButtonBounds, devilishButtonBounds;


    public LevelChoosingScreen(Main main) {
        Logger.info("Level Choosing screen");
        this.main = main;

        viewport = new FitViewport(10.66F, 6);
        this.main.updateFontScale(viewport);

        backgroundTexture = new Texture("GameScreen/level_choosing_background.jpg");
        setButtons();

        Logger.info("Created a level choosing screen successfully");
    }
    private void setButtons(){
        easyButtonBounds=new Rectangle(1.5f, 2, 3, 1);
        mediumButtonBounds=new Rectangle(5.5f, 2, 3, 1);
        hardButtonBounds=new Rectangle(1.5f, 0.5f, 3, 1);
        devilishButtonBounds=new Rectangle(5.5f, 0.5f, 3, 1);
    }
    private void analyzeTouch(Vector3 touchPos){//touch position is obtained  (in terms of x, y)
        viewport.unproject(touchPos);
        boolean clicked=easyButtonBounds.contains(touchPos.x, touchPos.y) ||
            mediumButtonBounds.contains(touchPos.x, touchPos.y) ||  hardButtonBounds.contains(touchPos.x, touchPos.y)
            || devilishButtonBounds.contains(touchPos.x, touchPos.y);
        if (clicked){
            Logger.info("Clicked");
            GameConfig config=DifficultyFactory.defaultGameConfig();
            if (easyButtonBounds.contains(touchPos.x, touchPos.y))
                config=DifficultyFactory.easy();
            else if (mediumButtonBounds.contains(touchPos.x, touchPos.y))
                config=DifficultyFactory.medium();
            else if (hardButtonBounds.contains(touchPos.x, touchPos.y))
                config=DifficultyFactory.hard();
            else if (devilishButtonBounds.contains(touchPos.x, touchPos.y))
                config=DifficultyFactory.devilish();
            main.setScreen(new LevelScreen(main, config));
        }
    }

    @Override
    public void show() {
        // Prepare your screen here
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
