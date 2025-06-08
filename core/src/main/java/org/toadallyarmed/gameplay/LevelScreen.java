package org.toadallyarmed.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import org.toadallyarmed.entity.EntityType;
import org.toadallyarmed.factory.*;
import org.toadallyarmed.system.SystemsManager;
import org.toadallyarmed.util.Debugging;
import org.toadallyarmed.util.logger.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class LevelScreen implements Screen {
    final Main main;
    final FitViewport viewport;

    final Texture backgroundTexture;

    final FrogFactory frogFactory;
    final CoinFactory coinFactory;
    private final GlobalGameState gameState;
    final GameConfig config;
    private final SystemsManager systemsManager;

    BitmapFont pixelFont, font;
    final WalletComponent wallet;
    AtomicInteger money;

    //--BUTTONS--//
    Rectangle buttonBoundsWizard, buttonBoundsBard, buttonBoundsKnight, buttonBoundsTank;
    FrogType bought=FrogType.NONE;
    final ConcurrentLinkedQueue<Entity> entities;
    final Set<Vector2> taken=new HashSet<>();

    public LevelScreen(Main main, GameConfig config) {
        Logger.info("Placeable Frogs screen");
        this.main = main;
        this.config = config;

        viewport = new FitViewport(10.66F, 6);
        this.main.updateFontScale(viewport);

        backgroundTexture = new Texture("GameScreen/level_background.jpg");

        frogFactory = FrogFactory.get();
        coinFactory = CoinFactory.get();
        gameState = new GlobalGameState(
            new WalletComponent(config.StartingMoney()),
            config,
            HedgehogFactory.get()
        );
        wallet=gameState.getWallet();
        systemsManager = SystemsManagerFactory.getSystemsManagerForGameplay(gameState);
        entities = gameState.getEntities();
        entities.add(coinFactory.createSpecialCoin(new Vector2(0, 5)));

        setFonts();
        setButtons();

        if (Debugging.debuggingMode()) {
            wallet.access().addAndGet(1000);
        }

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
        buttonBoundsWizard =new Rectangle(10.66F-1.5F, 5, 1.5F, 1);
        buttonBoundsKnight =new Rectangle(10.66F-3F, 5, 1.5F, 1);
        buttonBoundsBard =new Rectangle(10.66F-4.5F, 5, 1.5F, 1);
        buttonBoundsTank =new Rectangle(10.66F-6F, 5, 1.5F, 1);
    }
    private void analyzeTouch(Vector3 touchPos){//touch position is obtained  (in terms of x, y)
        viewport.unproject(touchPos);

        boolean hitWizard = buttonBoundsWizard.contains(touchPos.x, touchPos.y);
        boolean hitBard = buttonBoundsBard.contains(touchPos.x, touchPos.y);
        boolean hitKnight = buttonBoundsKnight.contains(touchPos.x, touchPos.y);
        boolean hitTank = buttonBoundsTank.contains(touchPos.x, touchPos.y);
        boolean onButton = hitWizard || hitBard || hitKnight || hitTank;

        if (bought == FrogType.NONE && onButton) {//bought a frog
            if (hitWizard) {
                int cost = config.wizardFrog().cost();
                if (cost <= wallet.access().get()) {
                    wallet.pay(cost);
                    Logger.info("Wizard bought");
                    bought = FrogType.WIZARD;
                }
            } else if (hitBard) {
                int cost = config.bardFrog().cost();
                if (cost <= wallet.access().get()) {
                    wallet.pay(cost);
                    Logger.info("Bard bought");
                    bought = FrogType.BARD;
                }
            } else if (hitKnight) {
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
            if (touchPos.y >= 0 && touchPos.y < 5f && touchPos.x >= 0 && touchPos.x < viewport.getWorldWidth()) { // Trying to place a frog.
                float cellWidth  = viewport.getWorldWidth()/10.66f;
                float cellHeight = viewport.getWorldHeight()/6;

                int cellX = MathUtils.clamp((int)(touchPos.x / cellWidth), 0, 10);
                int cellY = MathUtils.clamp((int)(touchPos.y / cellHeight), 0, 4);

                Vector2 gridPos = new Vector2(cellX, cellY);


                if (!taken.contains(gridPos) && cellX<=9) {
                    Entity entity = switch (bought) {
                        case BARD -> frogFactory.createBardFrog(gridPos, config.bardFrog());
                        case TANK -> frogFactory.createTankFrog(gridPos, config.tankFrog());
                        case KNIGHT -> frogFactory.createKnightFrog(gridPos, config.knightFrog());
                        default -> frogFactory.createWizardFrog(gridPos, config.wizardFrog());
                    };
                    entities.add(entity);
                    taken.add(gridPos);
                    bought = FrogType.NONE;
                    Logger.info("Frog placed");
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
        for (Entity entity : entities)
            if (!entity.isMarkedForRemoval()) {
                if (Debugging.debuggingMode()) {
                    if (entity.type() == EntityType.WINNING)
                        main.setScreen(new LevelVictoryScreen(main));
                    else if (entity.type() == EntityType.LOSING)
                        main.setScreen(new LevelFailScreen(main));
                }
            }

        money = wallet.access();
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
