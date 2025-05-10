package org.toadallyarmed.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import org.toadallyarmed.component.WorldTransformComponent;
import org.toadallyarmed.component.coin.AnimatedCoinRenderableComponent;
import org.toadallyarmed.component.coin.CoinState;
import org.toadallyarmed.component.coin.CoinStateComponent;
import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.entity.EntityType;
import org.toadallyarmed.util.AnimatedSprite;
import org.toadallyarmed.util.logger.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CoinFactory implements Disposable {
    private final Texture basicCoinTexture;
    private final TextureRegion basicCoinTextureRegion;

    Map<CoinState, AnimatedSprite> basicCoinAnimations, specialCoinAnimations;
    private static final CoinFactory CoinFactory = new CoinFactory();
    private CoinFactory() {
        Logger.trace("Initializing CoinFactory");
        basicCoinTexture= new Texture("GameScreen/Coins/coin.png");
        basicCoinTextureRegion = new TextureRegion(basicCoinTexture, 0, 0, 16, 16);

        setupAnimations();

        Logger.debug("Initialized CoinFactory successfully");
    }

    public static CoinFactory get() {
        return CoinFactory;
    }

    private void setupAnimations() {
        TextureRegion[][] tmp = TextureRegion.split(basicCoinTexture,
            basicCoinTexture.getWidth()/6,
            basicCoinTexture.getHeight());

        basicCoinAnimations = new HashMap<>();
        specialCoinAnimations = new HashMap<>();

        TextureRegion[] frames = Arrays.copyOfRange(tmp[0], 0, 5);
        Animation<TextureRegion> animation = new Animation<>(0.25f, frames);
        basicCoinAnimations.put(CoinState.SPIN, new AnimatedSprite(
            animation,
            new Vector2(0.37f, 0.64f),
            new Vector2(0.26f, 0.26f)
        ));
        basicCoinAnimations.put(CoinState.NONEXISTENT, AnimatedSprite.empty());

        animation = new Animation<>(0.5f, frames);
        specialCoinAnimations.put(CoinState.SPIN, new AnimatedSprite(
            animation,
            new Vector2(0.15f, 0.15f),
            new Vector2(0.7f, 0.7f)
        ));
        specialCoinAnimations.put(CoinState.NONEXISTENT, AnimatedSprite.empty());
    }

    public Entity createBasicCoin() {
        Logger.trace("Creating Coin Entity in factory");
        WorldTransformComponent transform = new WorldTransformComponent();
        CoinStateComponent CoinState = new CoinStateComponent();
        AnimatedCoinRenderableComponent renderable = new AnimatedCoinRenderableComponent(transform, CoinState, basicCoinAnimations);
        return new Entity.EntityBuilder(EntityType.COIN)
            .add(TransformComponent.class, transform)
            .add(StateComponent.class, CoinState)
            .add(RenderableComponent.class, renderable)
            .build();
    }
    public Entity createSpecialCoin() {
        Logger.trace("Creating Coin Entity in factory");
        WorldTransformComponent transform = new WorldTransformComponent();
        CoinStateComponent CoinState = new CoinStateComponent();
        AnimatedCoinRenderableComponent renderable = new AnimatedCoinRenderableComponent(transform, CoinState, specialCoinAnimations);
        return new Entity.EntityBuilder(EntityType.COIN)
            .add(TransformComponent.class, transform)
            .add(StateComponent.class, CoinState)
            .add(RenderableComponent.class, renderable)
            .build();
    }

    @Override
    public void dispose() {
        Logger.trace("Disposing CoinFactory");
        basicCoinTexture.dispose();
    }
}
