package org.toadallyarmed.testscreens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.toadallyarmed.Main;
import org.toadallyarmed.component.WalletComponent;
import org.toadallyarmed.factory.DifficultyFactory;
import org.toadallyarmed.factory.FrogFactory;
import org.toadallyarmed.factory.HedgehogFactory;
import org.toadallyarmed.factory.SystemsManagerFactory;
import org.toadallyarmed.gameplay.GlobalGameState;
import org.toadallyarmed.system.SystemsManager;
import org.toadallyarmed.util.logger.Logger;

import java.util.concurrent.ThreadLocalRandom;

public class Test2Screen implements Screen {
    final Main main;
    final FitViewport viewport;

    final Texture backgroundTexture;

    private final GlobalGameState gameState;
    private final SystemsManager systemsManager;

    public Test2Screen(Main main) {
        this.main = main;

        final var config = DifficultyFactory.debug();

        viewport = new FitViewport(10.66F, 6);
        this.main.updateFontScale(viewport);

        backgroundTexture = new Texture("GameScreen/level_background.jpg");

        final var frogFactory = FrogFactory.get();
        gameState = new GlobalGameState(
            new WalletComponent(config.StartingMoney()),
            config,
            HedgehogFactory.get()
        );
        final var wallet=gameState.getWallet();
        systemsManager = SystemsManagerFactory.getSystemsManagerForGameplay(gameState);
        final var entities = gameState.getEntities();

        for (int i = 0; i < 8; i++) {
            entities.add(frogFactory.createWizardFrog(new Vector2(i, 4), config.wizardFrog()));
            entities.add(frogFactory.createWizardFrog(new Vector2(i, 3), config.wizardFrog()));
            entities.add(frogFactory.createWizardFrog(new Vector2(i, 2), config.wizardFrog()));
            entities.add(frogFactory.createWizardFrog(new Vector2(i, 1), config.wizardFrog()));
            entities.add(frogFactory.createWizardFrog(new Vector2(i, 0), config.wizardFrog()));
        }
        var hedgehogFactory = HedgehogFactory.get();
        for (int i = 0; i < 500; i++) {
            Vector2 pos = new Vector2(11.f + 0.01f * i, ThreadLocalRandom.current().nextInt(0, 5));
            entities.add(hedgehogFactory.createHealthyHedgehog(pos, config.healthyHedgehog()));
        }
        wallet.access().addAndGet(1000);

        Logger.info("Created a new gameplay screen successfully");
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

        main.renderer.getSpriteBatch().end();
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        main.updateFontScale(viewport);
    }

    @Override
    public void pause() {
        systemsManager.pause();
    }

    @Override
    public void resume() {
        systemsManager.resume();
    }

    @Override
    public void hide() {
        systemsManager.stop();
    }

    @Override
    public void dispose() {
        Logger.info("disposing a gameplay screen");
        backgroundTexture.dispose();
        systemsManager.stop();
    }
}
