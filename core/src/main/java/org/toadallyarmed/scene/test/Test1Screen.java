package org.toadallyarmed.scene.test;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.toadallyarmed.Main;
import org.toadallyarmed.entity.character.frog.FrogFactory;
import org.toadallyarmed.entity.character.hedgehog.HedgehogFactory;
import org.toadallyarmed.scene.gameplay.DifficultyFactory;
import org.toadallyarmed.scene.gameplay.WalletComponent;
import org.toadallyarmed.scene.gameplay.GlobalGameState;
import org.toadallyarmed.base.system.SystemsManager;
import org.toadallyarmed.scene.gameplay.SystemsManagerFactory;
import org.toadallyarmed.util.log.Logger;

public class Test1Screen implements Screen {
    final Main main;
    final FitViewport viewport;

    final Texture backgroundTexture;

    private final GlobalGameState gameState;
    private final SystemsManager systemsManager;

    public Test1Screen(Main main) {
        this.main = main;

        final var config = DifficultyFactory.debug();

        viewport = new FitViewport(10.66F, 6);
        this.main.updateFontScale(viewport);

        backgroundTexture = new Texture("assets/Scenes/level_background.jpg");

        final var frogFactory = FrogFactory.get();
        gameState = new GlobalGameState(
            new WalletComponent(config.StartingMoney()),
            config,
            HedgehogFactory.get()
        );
        final var wallet=gameState.getWallet();
        systemsManager = SystemsManagerFactory.getSystemsManagerForGameplay(gameState);
        final var entities = gameState.getEntities();

        entities.add(frogFactory.createWizardFrog(new Vector2(5, 4), config.wizardFrog()));
        entities.add(frogFactory.createWizardFrog(new Vector2(5, 3), config.wizardFrog()));
        entities.add(frogFactory.createWizardFrog(new Vector2(5, 2), config.wizardFrog()));
        entities.add(frogFactory.createWizardFrog(new Vector2(5, 1), config.wizardFrog()));
        entities.add(frogFactory.createWizardFrog(new Vector2(5, 0), config.wizardFrog()));
        entities.add(frogFactory.createWizardFrog(new Vector2(0, 4), config.wizardFrog()));
        entities.add(frogFactory.createWizardFrog(new Vector2(0, 3), config.wizardFrog()));
        entities.add(frogFactory.createWizardFrog(new Vector2(0, 2), config.wizardFrog()));
        entities.add(frogFactory.createWizardFrog(new Vector2(0, 1), config.wizardFrog()));
        entities.add(frogFactory.createWizardFrog(new Vector2(0, 0), config.wizardFrog()));

        wallet.increase(1000);

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
