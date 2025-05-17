package org.toadallyarmed.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static com.badlogic.gdx.math.MathUtils.floor;

public class ForButtonsScreen implements Screen {
    final Main main;
    FitViewport viewport;

    Texture backgroundTexture;

    FrogFactory frogFactory;
    private final GlobalGameState gameState;
    GameConfig config;
    private final SystemsManager systemsManager;

    BitmapFont pixelFont, font;
    WalletComponent wallet;
    AtomicInteger money;


    //--BUTTONS--//
    Texture buttonTexture, secondButtonTexture;
    Rectangle buttonBounds11, buttonBounds12, buttonBounds21, buttonBounds22, bigButtonBounds;
    OrthographicCamera camera;
    FrogType bought=FrogType.NONE;
    ConcurrentLinkedQueue<Entity> entities = new ConcurrentLinkedQueue<>();

    public ForButtonsScreen(Main main) {
        Logger.info("Working on Nat's screen");
        this.main = main;

        viewport = new FitViewport(10.66F, 6);
        this.main.updateFontScale(viewport);

        backgroundTexture = new Texture("GameScreen/background.jpg");

        frogFactory = FrogFactory.get();
        gameState = new GlobalGameState(
            new WalletComponent(0),
            DifficultyFactory.defaultGameConfig()
        );
        wallet=gameState.getWallet();
        systemsManager = SystemsManagerFactory.getSystemsManagerForGameplay(gameState);
        entities = gameState.getEntities();
        config = gameState.getGameConfig();

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
        buttonTexture=new Texture("GameScreen/emptyTexture.png");
        buttonBounds11=new Rectangle(10.66F-1.5F, 5, 1.5F, 1);
        buttonBounds12=new Rectangle(10.66F-4.5F, 5, 1.5F, 1);
        buttonBounds21=new Rectangle(10.66F-3F, 5, 1.5F, 1);
        buttonBounds22=new Rectangle(10.66F-6F, 5, 1.5F, 1);
        bigButtonBounds=new Rectangle(10.66F-6F, 5, 6F, 1);
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
            if (bigButtonBounds.contains(touchPos.x, touchPos.y)) {
                if (bought!=FrogType.NONE) {
                    pixelFont.draw(main.renderer.getSpriteBatch(), "Place your hero first", 1, 5);
                } else {
                    if (buttonBounds11.contains(touchPos.x, touchPos.y)) {
                        int cost = config.wizardFrog().cost();
                        if (cost <= wallet.access().get()) {
                            wallet.pay(cost);
                            Logger.info("Wizard bought");
                            bought = FrogType.WIZARD;
                        }
                    } else if (buttonBounds12.contains(touchPos.x, touchPos.y)) {
                        int cost = config.moneyFrog().cost();
                        if (cost <= wallet.access().get()) {
                            wallet.pay(cost);
                            Logger.info("Bard bought");
                            bought = FrogType.BARD;
                        }
                    } else if (buttonBounds21.contains(touchPos.x, touchPos.y)) {
                        int cost = config.knightFrog().cost();
                        if (cost <= wallet.access().get()) {
                            wallet.pay(cost);
                            Logger.info("Knight bought");
                            bought = FrogType.KNIGHT;
                        }
                    } else if (buttonBounds22.contains(touchPos.x, touchPos.y)) {
                        int cost = config.tankFrog().cost();
                        if (cost <= wallet.access().get()) {
                            wallet.pay(cost);
                            Logger.info("Tank bought");
                            bought = FrogType.TANK;
                        }
                    }
                }
            } else if (bought!=FrogType.NONE){
                Entity entity;
                touchPos.x=floor(touchPos.x);
                touchPos.y=floor(touchPos.y);
                if (bought==FrogType.BARD)
                    entity=frogFactory.createMoneyFrog(new Vector2(touchPos.x, touchPos.y), config.moneyFrog());
                else if (bought==FrogType.TANK)
                    entity=frogFactory.createTankFrog(new Vector2(touchPos.x, touchPos.y), config.tankFrog());
                else if (bought==FrogType.KNIGHT)
                    entity=frogFactory.createKnightFrog(new Vector2(touchPos.x, touchPos.y), config.knightFrog());
                else
                    entity=frogFactory.createWizardFrog(new Vector2(touchPos.x, touchPos.y), config.wizardFrog());
                entities.add(entity);
                bought=FrogType.NONE;
            } //TODO: check whether that place is taken
        }
        main.renderer.getSpriteBatch().draw(buttonTexture, buttonBounds11.x, buttonBounds11.y, buttonBounds11.width, buttonBounds11.height);
        main.renderer.getSpriteBatch().draw(buttonTexture, buttonBounds12.x, buttonBounds12.y, buttonBounds12.width, buttonBounds12.height);
        main.renderer.getSpriteBatch().draw(buttonTexture, buttonBounds21.x, buttonBounds21.y, buttonBounds21.width, buttonBounds21.height);
        main.renderer.getSpriteBatch().draw(buttonTexture, buttonBounds22.x, buttonBounds22.y, buttonBounds22.width, buttonBounds22.height);


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
    }
}
