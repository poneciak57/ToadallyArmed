package org.toadallyarmed.gameplay;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.toadallyarmed.Main;

public class GameScreen implements Screen {
    final Main main;
    FitViewport viewport;

    Texture backgroundTexture;
    Texture testFrogTexture;
    TextureRegion testFrogTextureRegion;

    public GameScreen(Main main) {
        this.main = main;

        viewport = new FitViewport(12, 6);
        this.main.updateFontScale(viewport);

        backgroundTexture = new Texture("GameScreen/background.jpg");
        testFrogTexture = new Texture("GameScreen/Frogs/Wizard/FrogWizardAttack.png");
        testFrogTextureRegion = new TextureRegion(testFrogTexture, 0, 0, 86, 66);
    }

    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        main.spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        main.spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        main.spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        main.spriteBatch.draw(testFrogTextureRegion, 0, 0, 1, 1);

        main.font.draw(main.spriteBatch, "Hello World!", 0, worldHeight);

        main.spriteBatch.end();
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
        // Destroy screen's assets here.
        backgroundTexture.dispose();
        testFrogTexture.dispose();
    }
}
