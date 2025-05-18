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
    Rectangle buttonBounds11, buttonBounds12, buttonBounds21, buttonBounds22;
    OrthographicCamera camera;
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
            DifficultyFactory.defaultGameConfig()
        );
        wallet=gameState.getWallet();
        systemsManager = SystemsManagerFactory.getSystemsManagerForGameplay(gameState);
        entities = gameState.getEntities();
        config = gameState.getGameConfig();

        coinFactory = CoinFactory.get();
        entities.add(coinFactory.createSpecialCoin(new Vector2(0, 5)));

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
        buttonBounds11=new Rectangle(10.66F-1.5F, 5, 1.5F, 1);
        buttonBounds21=new Rectangle(10.66F-3F, 5, 1.5F, 1);
        buttonBounds12=new Rectangle(10.66F-4.5F, 5, 1.5F, 1);
        buttonBounds22=new Rectangle(10.66F-6F, 5, 1.5F, 1);
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

        // RYSOWANIE TŁA I GRY
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        main.renderer.getSpriteBatch().draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        main.renderingSystem.tick(delta, gameState.getEntities());

        if (Gdx.input.justTouched()) {
            // 1) pobranie i konwersja współrzędnych
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            // 2) detekcja kliknięcia w przycisk
            boolean hit11 = buttonBounds11.contains(touchPos.x, touchPos.y);
            boolean hit12 = buttonBounds12.contains(touchPos.x, touchPos.y);
            boolean hit21 = buttonBounds21.contains(touchPos.x, touchPos.y);
            boolean hit22 = buttonBounds22.contains(touchPos.x, touchPos.y);
            boolean onButton = hit11 || hit12 || hit21 || hit22;

            // --- 3A) JEŚLI nic nie kupione i klik w przycisk → kupno ---
            if (bought == FrogType.NONE && onButton) {
                if (hit11) {
                    int cost = config.wizardFrog().cost();
                    if (cost <= wallet.access().get()) {
                        wallet.pay(cost);
                        Logger.info("Wizard bought");
                        bought = FrogType.WIZARD;
                    }
                } else if (hit12) {
                    int cost = config.moneyFrog().cost();
                    if (cost <= wallet.access().get()) {
                        wallet.pay(cost);
                        Logger.info("Bard bought");
                        bought = FrogType.BARD;
                    }
                } else if (hit21) {
                    int cost = config.knightFrog().cost();
                    if (cost <= wallet.access().get()) {
                        wallet.pay(cost);
                        Logger.info("Knight bought");
                        bought = FrogType.KNIGHT;
                    }
                } else { // hit22
                    int cost = config.tankFrog().cost();
                    if (cost <= wallet.access().get()) {
                        wallet.pay(cost);
                        Logger.info("Tank bought");
                        bought = FrogType.TANK;
                    }
                }
            }
            // --- 3B) JEŚLI coś kupione i klik poza przyciskami → stawianie ---
            else if (bought != FrogType.NONE && !onButton) {
                // 1) upewnij się, że klik jest wewnątrz regionu 10 krat (0 ≤ x < 10.66, y < 5)
                if (touchPos.y >= 0 && touchPos.y < 5f && touchPos.x >= 0 && touchPos.x < viewport.getWorldWidth()) {
                    // 2) oblicz szerokość jednej kratki na podstawie całkowitej szerokości viewportu i liczby kolumn (10)
                    float cellWidth  = viewport.getWorldWidth()  / 10f;  // ≈1.066
                    float cellHeight = 5f / 5f;                        // jeśli masz 5 wierszy, inaczej dostosuj

                    // 3) indeksy komórek przez dzielenie, a nie floor całego współrzędnego
                    int cellX = MathUtils.clamp((int)(touchPos.x / cellWidth), 0, 9);
                    int cellY = MathUtils.clamp((int)(touchPos.y / cellHeight), 0, 4);

                    Vector2 gridPos = new Vector2(cellX, cellY);

                    if (!taken.contains(gridPos)) {
                        Entity entity;
                        switch (bought) {
                            case BARD:
                                entity = frogFactory.createMoneyFrog(gridPos, config.moneyFrog());
                                break;
                            case TANK:
                                entity = frogFactory.createTankFrog(gridPos, config.tankFrog());
                                break;
                            case KNIGHT:
                                entity = frogFactory.createKnightFrog(gridPos, config.knightFrog());
                                break;
                            default: // WIZARD
                                entity = frogFactory.createWizardFrog(gridPos, config.wizardFrog());
                        }
                        entities.add(entity);
                        taken.add(gridPos);
                        bought = FrogType.NONE;
                    }
                }
            }
            // w każdym innym przypadku (klik w UI po zakupie, albo poza UI bez zakupu) – ignorujemy
        }

        // RYSOWANIE PRZYCISKÓW
        SpriteBatch sb = main.renderer.getSpriteBatch();
        money = wallet.access();
        pixelFont.draw(sb, Integer.toString(money.get()), 1, 6);
        sb.draw(buttonTexture, 10.66F-1.5F, 5, 1.5F, 1);

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
