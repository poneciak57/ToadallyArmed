package org.toadallyarmed.gameplay;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.toadallyarmed.Main;
import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.factory.FrogFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GameplayScreen implements Screen {
    final Main main;
    FitViewport viewport;

    Texture backgroundTexture;

    FrogFactory frogFactory;
    ConcurrentLinkedQueue<Entity> entities = new ConcurrentLinkedQueue<>();

    public GameplayScreen(Main main) {
        this.main = main;

        viewport = new FitViewport(10.66F, 6);
        this.main.updateFontScale(viewport);

        backgroundTexture = new Texture("GameScreen/background.jpg");

        frogFactory = FrogFactory.get();
        Entity basicFrog = frogFactory.createBasicFrog();
        basicFrog.get(TransformComponent.class).get().setPosition(new Vector2(3, 2));
        entities.add(basicFrog);
    }

    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.

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
            renderable.render(main.renderer);
        }

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
        // Destroy screen's assets here.
        backgroundTexture.dispose();
        frogFactory.dispose();
    }
}
