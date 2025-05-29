package org.toadallyarmed.testscreens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.toadallyarmed.Main;
import org.toadallyarmed.component.AliveEntityStateComponent;
import org.toadallyarmed.component.WalletComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.factory.*;
import org.toadallyarmed.gameplay.GlobalGameState;
import org.toadallyarmed.state.BooleanState;
import org.toadallyarmed.state.FrogState;
import org.toadallyarmed.system.SystemsManager;
import org.toadallyarmed.util.logger.Logger;
import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.util.rendering.effect.fog.FogEffect;

public class BulletDamageTestScreen implements Screen {
    final Main main;
    final FitViewport viewport;

    final Texture backgroundTexture;

    private final GlobalGameState gameState;
    private final SystemsManager systemsManager;

    private final float TILE_SIZE = 48;
    final private FogEffect fogEffect;

    public BulletDamageTestScreen(Main main) {
        Logger.info("Placeable Frogs screen");
        this.main = main;

        final var config = DifficultyFactory.devilish();

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

        fogEffect = new FogEffect(new Vector2(viewport.getWorldWidth() * TILE_SIZE, viewport.getWorldHeight() * TILE_SIZE), TILE_SIZE);

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

        fogEffect.render(main.renderer);

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
