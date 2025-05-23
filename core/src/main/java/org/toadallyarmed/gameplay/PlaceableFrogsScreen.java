package org.toadallyarmed.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import org.toadallyarmed.component.hedgehog.HedgehogState;
import org.toadallyarmed.config.GameConfig;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.factory.*;
import org.toadallyarmed.system.SystemsManager;
import org.toadallyarmed.util.logger.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static com.badlogic.gdx.math.MathUtils.floor;

public class PlaceableFrogsScreen implements Screen {
    final Main main;
    FitViewport viewport;

    Texture backgroundTexture;

    FrogFactory frogFactory;
    CoinFactory coinFactory;
    private final GlobalGameState gameState;
    GameConfig config;
    private final SystemsManager systemsManager;

    BitmapFont pixelFont, font;
    WalletComponent wallet;
    AtomicInteger money;

    //--BUTTONS--//
    Texture buttonTexture;
    Rectangle buttonBoundswizard, buttonBoundsbard, buttonBoundsknight, buttonBoundstank;
    FrogType bought=FrogType.NONE;
    ConcurrentLinkedQueue<Entity> entities;
    Set<Vector2> taken=new HashSet<>();


    public PlaceableFrogsScreen(Main main) {
        Logger.info("Working on Nat's screen");
        this.main = main;

        viewport = new FitViewport(10.66F, 6);
        this.main.updateFontScale(viewport);

        backgroundTexture = new Texture("GameScreen/background.jpg");

        frogFactory = FrogFactory.get();
        coinFactory = CoinFactory.get();
        gameState = new GlobalGameState(
            new WalletComponent(0),
            DifficultyFactory.defaultGameConfig(),
            new HedgehogFactory()
        );
        wallet=gameState.getWallet();
        systemsManager = SystemsManagerFactory.getSystemsManagerForGameplay(gameState);
        entities = gameState.getEntities();
        config = gameState.getGameConfig();


        entities.add(coinFactory.createSpecialCoin(new Vector2(0, 5)));

        setFonts();
        setButtons();

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
    private void setButtons(){
        buttonTexture=new Texture("GameScreen/button.png");
        buttonBoundswizard=new Rectangle(10.66F-1.5F, 5, 1.5F, 1);
        buttonBoundsknight=new Rectangle(10.66F-3F, 5, 1.5F, 1);
        buttonBoundsbard=new Rectangle(10.66F-4.5F, 5, 1.5F, 1);
        buttonBoundstank=new Rectangle(10.66F-6F, 5, 1.5F, 1);
    }
    private void analyzeTouch(Vector3 touchPos){//touch position is obtained  (in terms of x, y)
        viewport.unproject(touchPos);

        boolean hitwizard = buttonBoundswizard.contains(touchPos.x, touchPos.y);
        boolean hitbard = buttonBoundsbard.contains(touchPos.x, touchPos.y);
        boolean hitknight = buttonBoundsknight.contains(touchPos.x, touchPos.y);
        boolean hittank = buttonBoundstank.contains(touchPos.x, touchPos.y);
        boolean onButton = hitwizard || hitbard || hitknight || hittank;

        if (bought == FrogType.NONE && onButton) {//bought a frog
            if (hitwizard) {
                int cost = config.wizardFrog().cost();
                if (cost <= wallet.access().get()) {
                    wallet.pay(cost);
                    Logger.info("Wizard bought");
                    bought = FrogType.WIZARD;
                }
            } else if (hitbard) {
                int cost = config.moneyFrog().cost();
                if (cost <= wallet.access().get()) {
                    wallet.pay(cost);
                    Logger.info("Bard bought");
                    bought = FrogType.BARD;
                }
            } else if (hitknight) {
                int cost = config.knightFrog().cost();
                if (cost <= wallet.access().get()) {
                    wallet.pay(cost);
                    Logger.info("Knight bought");
                    bought = FrogType.KNIGHT;
                }
            } else {
                int cost = config.tankFrog().cost();
                if (cost <= wallet.access().get()) {
                    wallet.pay(cost);
                    Logger.info("Tank bought");
                    bought = FrogType.TANK;
                }
            }
        }
        else if (bought != FrogType.NONE && !onButton) {
            if (touchPos.y >= 0 && touchPos.y < 5f && touchPos.x >= 0 && touchPos.x < viewport.getWorldWidth()) {//tryna place a frog
                float cellWidth  = viewport.getWorldWidth()/10.66f;
                float cellHeight = viewport.getWorldHeight()/6;

                int cellX = MathUtils.clamp((int)(touchPos.x / cellWidth), 0, 10);
                int cellY = MathUtils.clamp((int)(touchPos.y / cellHeight), 0, 4);

                Vector2 gridPos = new Vector2(cellX, cellY);


                if (!taken.contains(gridPos) && cellX<=9) {
                    Entity entity = switch (bought) {
                        case BARD -> frogFactory.createMoneyFrog(gridPos, config.moneyFrog());
                        case TANK -> frogFactory.createTankFrog(gridPos, config.tankFrog());
                        case KNIGHT -> frogFactory.createKnightFrog(gridPos, config.knightFrog());
                        default -> frogFactory.createWizardFrog(gridPos, config.wizardFrog());
                    };
                    entities.add(entity);
                    taken.add(gridPos);
                    bought = FrogType.NONE;
                }
            }
        }
    }

    @Override
    public void show() {
        systemsManager.start();
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
        main.renderingSystem.tick(delta, gameState.getEntities());

        if (Gdx.input.justTouched())
            analyzeTouch(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        SpriteBatch sb = main.renderer.getSpriteBatch();
        money = wallet.access();
        pixelFont.draw(sb, Integer.toString(money.get()), 1, 6);
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
        buttonTexture.dispose();
        systemsManager.stop();
    }
}
