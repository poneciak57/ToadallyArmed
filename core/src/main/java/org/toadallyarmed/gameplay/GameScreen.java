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
    Texture moneyFrogTexture, tankFrogTexture, knightFrogTexture, basicFrogTexture, wizardFrogTexture;
    Texture randomHedgehogTexture;
    Texture coinTexture;
    TextureRegion moneyFrogTextureRegion, tankFrogTextureRegion, knightFrogTextureRegion, basicFrogTextureRegion, wizardFrogTextureRegion;
    TextureRegion randomHedgehogTextureRegion;
    TextureRegion coinTextureRegion;

    //BitmapFont pixelFont;

    public GameScreen(Main main) {
        this.main = main;

        viewport = new FitViewport(10.66F, 6);
        this.main.updateFontScale(viewport);

        backgroundTexture = new Texture("GameScreen/background.jpg");
        moneyFrogTexture = new Texture("GameScreen/Frogs/moneyFrog.png");
        tankFrogTexture = new Texture("GameScreen/Frogs/tankFrog.png");
        knightFrogTexture = new Texture("GameScreen/Frogs/knightFrog.png");
        basicFrogTexture= new Texture("GameScreen/Frogs/basicFrog.png");
        wizardFrogTexture = new Texture("GameScreen/Frogs/wizardFrog.png");
        randomHedgehogTexture= new Texture("GameScreen/Hedgehogs/purpleHedgehog.png");
        coinTexture = new Texture("GameScreen/Coins/coin.png");

        moneyFrogTextureRegion = new TextureRegion(moneyFrogTexture, 0, 0, 44, 33);
        tankFrogTextureRegion = new TextureRegion(tankFrogTexture, 0, 0, 44, 33);
        knightFrogTextureRegion = new TextureRegion(knightFrogTexture, 0, 0, 44, 33);
        basicFrogTextureRegion = new TextureRegion(basicFrogTexture, 0, 0, 44, 33);
        wizardFrogTextureRegion = new TextureRegion(wizardFrogTexture, 0, 0, 44, 33);
        randomHedgehogTextureRegion= new TextureRegion(randomHedgehogTexture, 120, 80, 24, 16);
        coinTextureRegion=new TextureRegion(coinTexture, 0, 0, 16, 16);
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

        main.spriteBatch.draw(moneyFrogTextureRegion,  -0.3F, 0.1F, 1.5F, 1.5F);
        main.spriteBatch.draw(tankFrogTextureRegion,  -0.3F, 1.1F, 1.5F, 1.5F);
        main.spriteBatch.draw(wizardFrogTextureRegion, -0.3F, 2.1F, 1.5F, 1.5F);
        main.spriteBatch.draw(knightFrogTextureRegion, -0.3F, 3.1F, 1.5F, 1.5F);
        main.spriteBatch.draw(basicFrogTextureRegion, -0.3F, 4.1F, 1.5F, 1.5F);
        main.spriteBatch.draw(randomHedgehogTextureRegion, 1, 1.1F, 0.9F, 0.9F);
        main.spriteBatch.draw(coinTextureRegion,  0.15F, 5.15F, 0.7F, 0.7F);


        //tryna get it to work
        //pixelFont=new BitmapFont(Gdx.files.internal("GameScreen/Fonts/2.fnt"));
        main.font.draw(main.spriteBatch, "You broke hehe", 1, worldHeight-0.5F);

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
        moneyFrogTexture.dispose();
        tankFrogTexture.dispose();
        knightFrogTexture.dispose();
        basicFrogTexture.dispose();
        wizardFrogTexture.dispose();
        coinTexture.dispose();
        randomHedgehogTexture.dispose();
    }
}
