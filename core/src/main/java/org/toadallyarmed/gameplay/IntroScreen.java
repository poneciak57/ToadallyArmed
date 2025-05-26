package org.toadallyarmed.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.math.Rectangle;
import org.toadallyarmed.Main;
import org.toadallyarmed.component.WalletComponent;
import org.toadallyarmed.component.frog.FrogType;
import org.toadallyarmed.config.GameConfig;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.factory.*;
import org.toadallyarmed.system.SystemsManager;
import org.toadallyarmed.util.logger.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class IntroScreen implements Screen {
    final Main main;
    final FitViewport viewport;

    final Texture backgroundTexture;

    final FrogFactory frogFactory;
    private final GlobalGameState gameState;
    final GameConfig config;
    private final SystemsManager systemsManager;

    //--BUTTONS--//
    Rectangle startButtonBounds;
    final ConcurrentLinkedQueue<Entity> entities;


    public IntroScreen(Main main) {
        Logger.info("Introduction screen");
        this.main = main;

        viewport = new FitViewport(10.66F, 6);
        this.main.updateFontScale(viewport);

        backgroundTexture = new Texture("GameScreen/intro_background.jpg");

        frogFactory = FrogFactory.get();
        gameState = new GlobalGameState(
            new WalletComponent(DifficultyFactory.defaultGameConfig().StartingMoney()),
            DifficultyFactory.defaultGameConfig(),
            null
        );
        systemsManager = SystemsManagerFactory.getSystemsManagerForGameplay(gameState);
        entities = gameState.getEntities();
        config = gameState.getGameConfig();

        setButtons();

        Logger.info("Created a new gameplay screen successfully");
    }
    private void setButtons(){
        startButtonBounds =new Rectangle(4f, 1, 3, 1);
    }
    private void analyzeTouch(Vector3 touchPos){//touch position is obtained  (in terms of x, y)
        viewport.unproject(touchPos);
        if (startButtonBounds.contains(touchPos.x, touchPos.y)){
            Logger.info("Clicked");
            main.setScreen(new PlaceableFrogsScreen(main));
        }

    }

    @Override
    public void show() {
        systemsManager.start();
        // Prepare your screen here.
        entities.add(FrogFactory.get().createKnightFrog(new Vector2(8, 3.735f), config.knightFrog()));
        //add here yellow hopping frog
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
    }
}
