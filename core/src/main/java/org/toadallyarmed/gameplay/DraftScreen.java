package org.toadallyarmed.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.toadallyarmed.Main;
import org.toadallyarmed.util.AnimatedSprite;

import java.util.Arrays;

public class DraftScreen implements Screen {
    final Main main;
    final SpriteBatch spriteBatch;
    FitViewport viewport;

    float animationTimer = 0f;

    Texture backgroundTexture;
    Texture moneyFrogTexture, tankFrogTexture, knightFrogTexture, basicFrogTexture, wizardFrogTexture;
    Texture randomHedgehogTexture;
    Texture coinTexture;
    Animation<TextureRegion> basicFrogAnimation;
    AnimatedSprite basicFrogAnimationSprite;

    BitmapFont pixelFont, font;

    public DraftScreen(Main main) {
        this.main = main;
        this.spriteBatch = main.renderer.getSpriteBatch();

        viewport = new FitViewport(10.66F, 6);
        this.main.updateFontScale(viewport);

        backgroundTexture = new Texture("GameScreen/background.jpg");
        setFrogTextures();
        setFrogAnimations();
        setFonts();
    }
    private void setFrogTextures(){
        //upload files
        moneyFrogTexture = new Texture("GameScreen/Frogs/moneyFrog.png");
        tankFrogTexture = new Texture("GameScreen/Frogs/tankFrog.png");
        knightFrogTexture = new Texture("GameScreen/Frogs/knightFrog.png");
        basicFrogTexture= new Texture("GameScreen/Frogs/basicFrog.png");
        wizardFrogTexture = new Texture("GameScreen/Frogs/wizardFrog.png");
        randomHedgehogTexture= new Texture("GameScreen/Hedgehogs/basicHedgehog.png");
        coinTexture = new Texture("GameScreen/Coins/coin.png");
    }
    private void setFrogAnimations() {
        TextureRegion[][] tmp = TextureRegion.split(basicFrogTexture,
            basicFrogTexture.getWidth() / 9,
            basicFrogTexture.getHeight() / 5);
        TextureRegion[] frames = Arrays.copyOfRange(tmp[0], 0, 8);
        basicFrogAnimation = new Animation<>(0.04f, frames);
        basicFrogAnimationSprite = new AnimatedSprite(
            basicFrogAnimation,
            new Vector2(-0.3F, 0.1F),
            new Vector2(1.5F, 1.5F)
        );
    }
    private void drawAnimations(float deltaTime) {
        animationTimer += deltaTime;
        TextureRegion currentFrame = basicFrogAnimation.getKeyFrame(animationTimer, true);
        // spriteBatch.draw(currentFrame, 0, 0);
        spriteBatch.draw(currentFrame, -0.3F, 0.1F, 1.5F, 1.5F);
        basicFrogAnimationSprite.render(
            main.renderer,
            new Vector2(0, 1),
            animationTimer);
    }

    private void setFonts(){
        font=new BitmapFont();
        pixelFont=new BitmapFont(Gdx.files.internal("GameScreen/Fonts/font.fnt"));
        float targetFontHeight = 0.7F;
        float scale = targetFontHeight/pixelFont.getCapHeight();
        pixelFont.getData().setScale(scale);
        pixelFont.setUseIntegerPositions(false);
        font.getData().setScale(scale);
        font.setColor(Color.RED);
        font.setUseIntegerPositions(false);
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
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);

        drawAnimations(delta);

        font.draw(spriteBatch, "jest w pyte", 1, 1);
        pixelFont.draw(spriteBatch, "Money", 1, 6);

        spriteBatch.end();
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
        pixelFont.dispose();
        font.dispose();
    }
}
