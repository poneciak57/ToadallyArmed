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
import org.toadallyarmed.component.frog.FrogState;
import org.toadallyarmed.component.frog.FrogStateComponent;
import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.factory.FrogFactory;
import org.toadallyarmed.factory.HedgehogFactory;
import org.toadallyarmed.util.logger.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GameplayScreen implements Screen {
    final Main main;
    FitViewport viewport;

    Texture backgroundTexture;

    FrogFactory frogFactory;
    HedgehogFactory hedgehogFactory;
    ConcurrentLinkedQueue<Entity> entities = new ConcurrentLinkedQueue<>();
    Entity basicFrog;

    BitmapFont pixelFont, font;
    Integer money=100;

    public GameplayScreen(Main main) {
        Logger.info("creating a new gameplay screen");
        this.main = main;

        viewport = new FitViewport(10.66F, 6);
        this.main.updateFontScale(viewport);

        backgroundTexture = new Texture("GameScreen/background.jpg");

        frogFactory = FrogFactory.get();
        basicFrog = frogFactory.createBasicFrog();
        basicFrog.get(TransformComponent.class).get().setPosition(new Vector2(0, 0));
        entities.add(basicFrog);
        hedgehogFactory = HedgehogFactory.get();
        Entity basicHedgehog = hedgehogFactory.createFastHedgehog();
        basicHedgehog.get(TransformComponent.class).get().setPosition(new Vector2(10, 1));
        entities.add(basicHedgehog);

        setFonts();

        Logger.info("created a new gameplay screen successfully");
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
        // Prepare your screen here.
    }

    float stateSwitchTimer = 0f;
    int frogStateID = 0;
    void updateBasicFrogState(float deltaTime) {
        FrogStateComponent frogStateComponent = (FrogStateComponent) basicFrog.get(StateComponent.class).get();
        stateSwitchTimer += deltaTime;
        if (stateSwitchTimer >= 2f) {
            stateSwitchTimer = 0f;
            frogStateID = (frogStateID + 1) % 3;
            switch(frogStateID) {
                case 0: frogStateComponent.setNextGeneralState(FrogState.IDLE); break;
                case 1: frogStateComponent.setNextGeneralState(FrogState.ACTION); break;
                case 2: frogStateComponent.setNextGeneralState(FrogState.DYING); break;
                default: break;
            }
        }
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.

        updateBasicFrogState(delta);

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        main.renderer.getSpriteBatch().setProjectionMatrix(viewport.getCamera().combined);
        main.renderer.getSpriteBatch().begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        main.renderer.getSpriteBatch().draw(backgroundTexture, 0, 0, worldWidth, worldHeight);

        for (Entity entity : entities) {
            var renderableOptional = entity.get(RenderableComponent.class);
            if (renderableOptional.isEmpty())
                continue;
            var renderable = renderableOptional.get();
            renderable.render(main.renderer, delta);
        }

        //font.draw(main.renderer.getSpriteBatch(), "jest w pyte", 1, 1);
        pixelFont.draw(main.renderer.getSpriteBatch(), Integer.toString(money), 1, 6);

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
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        Logger.info("disposing a gameplay screen");
        // Destroy screen's assets here.
        backgroundTexture.dispose();
        frogFactory.dispose();
    }
}
