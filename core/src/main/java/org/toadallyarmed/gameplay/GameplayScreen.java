package org.toadallyarmed.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.toadallyarmed.Main;
import org.toadallyarmed.component.WalletComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.factory.*;
import org.toadallyarmed.system.SystemsManager;
import org.toadallyarmed.util.logger.Logger;

import java.awt.*;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class GameplayScreen implements Screen {
    final Main main;
    FitViewport viewport;

    Texture backgroundTexture;

    FrogFactory frogFactory;
    HedgehogFactory hedgehogFactory;
    CoinFactory coinFactory;
    BulletFactory bulletFactory;
    ConcurrentLinkedQueue<Entity> entities = new ConcurrentLinkedQueue<>();
    private final GlobalGameState gameState;
    private final SystemsManager systemsManager;

    BitmapFont pixelFont, font;
    WalletComponent wallet;
    AtomicInteger money;


    //--BUTTONS--//
    Texture buttonTexture;
    Rectangle buttonBounds;
    boolean buttonClicked = false;
    OrthographicCamera camera;


    public GameplayScreen(Main main) {
        Logger.info("creating a new gameplay screen");
        this.main = main;

        viewport = new FitViewport(10.66F, 6);
        this.main.updateFontScale(viewport);

        backgroundTexture = new Texture("GameScreen/background.jpg");

        gameState = new GlobalGameState(
            new WalletComponent(0),
            DifficultyFactory.defaultGameConfig()
        );
        wallet=gameState.getWallet();
        systemsManager = SystemsManagerFactory.getSystemsManagerForGameplay(gameState);
        ConcurrentLinkedQueue<Entity> entities = gameState.getEntities();

        setFonts();

        Logger.info("Created a new gameplay screen successfully");
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
        systemsManager.start();
        // Prepare your screen here.

        //--BUTTONS--//
        buttonTexture=new Texture("GameScreen/button.png");
        buttonBounds=new Rectangle(1, 1, 2, 2);
        camera=new OrthographicCamera();
        camera.setToOrtho(false, 10.66F, 6);
        camera.update();
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

        main.renderingSystem.tick(delta, gameState.getEntities());

        //-- BUTTONS --//
        if (Gdx.input.justTouched()){
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (buttonBounds.contains(touchPos.x, touchPos.y)){
                buttonClicked=true;
                Logger.info("Button clicked");
            }
        }
        main.renderer.getSpriteBatch().draw(buttonTexture, buttonBounds.x, buttonBounds.y, buttonBounds.width, buttonBounds.height);
        if (buttonClicked){
            pixelFont.draw(main.renderer.getSpriteBatch(), "Do not touch me", 1, 4);

        }





        //-- END --//
        money=wallet.access();
        pixelFont.draw(main.renderer.getSpriteBatch(), Integer.toString(money.get()), 1, 6);

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
        systemsManager.pause();
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
        systemsManager.resume();
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
        systemsManager.stop();
    }

    @Override
    public void dispose() {
        Logger.info("disposing a gameplay screen");
        // Destroy screen's assets here.
        backgroundTexture.dispose();
        systemsManager.stop();
        frogFactory.dispose();
        hedgehogFactory.dispose();
        coinFactory.dispose();
        bulletFactory.dispose();
    }
}
