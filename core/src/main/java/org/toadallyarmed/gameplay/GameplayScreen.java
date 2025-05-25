package org.toadallyarmed.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.toadallyarmed.Main;
import org.toadallyarmed.component.WalletComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.factory.*;
import org.toadallyarmed.system.SystemsManager;
import org.toadallyarmed.util.logger.Logger;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class GameplayScreen implements Screen {
    final Main main;
    final FitViewport viewport;

    final Texture backgroundTexture;

    final FrogFactory frogFactory;
    final HedgehogFactory hedgehogFactory;
    final CoinFactory coinFactory;
    final BulletFactory bulletFactory;
    final ConcurrentLinkedQueue<Entity> entities;
    private final GlobalGameState gameState;
    private final SystemsManager systemsManager;

    BitmapFont pixelFont, font;
    final WalletComponent wallet;
    AtomicInteger money;

    public GameplayScreen(Main main) {
        Logger.info("creating a new gameplay screen");
        this.main = main;

        viewport = new FitViewport(10.66F, 6);
        this.main.updateFontScale(viewport);

        backgroundTexture = new Texture("GameScreen/background.jpg");

        frogFactory = FrogFactory.get();
        hedgehogFactory = HedgehogFactory.get();
        gameState = new GlobalGameState(
            new WalletComponent(0),
            DifficultyFactory.defaultGameConfig()
        );
        wallet=gameState.getWallet();
        systemsManager = SystemsManagerFactory.getSystemsManagerForGameplay(gameState);
        entities = gameState.getEntities();
        var config = gameState.getGameConfig();
        Entity basicFrog  = frogFactory.createBasicFrog(new Vector2(0, 0), config.knightFrog());
        Entity knightFrog = frogFactory.createKnightFrog(new Vector2(0, 1), config.knightFrog());
        Entity moneyFrog  = frogFactory.createMoneyFrog(new Vector2(0, 2), config.moneyFrog());
        Entity tankFrog   = frogFactory.createTankFrog(new Vector2(0, 3), config.tankFrog());
        Entity wizardFrog = frogFactory.createWizardFrog(new Vector2(0, 4), config.wizardFrog());

        Entity basicHedgehog = hedgehogFactory.createBasicHedgehog(new Vector2(9, 1), config.basicHedgehog());
        Entity fastHedgehog = hedgehogFactory.createFastHedgehog(new Vector2(9, 2), config.fastHedgehog());
        Entity strongHedgehog = hedgehogFactory.createStrongHedgehog(new Vector2(9, 3), config.strongHedgehog());
        Entity healthyHedgehog = hedgehogFactory.createHealthyHedgehog(new Vector2(9, 4), config.healthyHedgehog());

        coinFactory = CoinFactory.get();
        Entity coin = coinFactory.createCoin(new Vector2(0, 2));
        Entity Scoin = coinFactory.createSpecialCoin(new Vector2(0, 5));
        bulletFactory = BulletFactory.get();
        Entity real = bulletFactory.createFireball(new Vector2(3, 3), config.BulletSystemTickRate());
        Entity fake = bulletFactory.createBullet(new Vector2(4, 4), config.BulletSystemTickRate());

        entities.addAll(List.of(
            basicFrog, knightFrog, moneyFrog, tankFrog, wizardFrog,
            basicHedgehog, fastHedgehog, strongHedgehog, healthyHedgehog,
            coin, Scoin, real, fake
        ));

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
    }
}
