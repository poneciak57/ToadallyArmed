package org.toadallyarmed.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import org.toadallyarmed.component.WorldTransformComponent;
import org.toadallyarmed.component.coin.CoinRenderableComponent;
import org.toadallyarmed.component.coin.CoinState;
import org.toadallyarmed.component.coin.CoinStateComponent;
import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.config.AnimationConfig;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.entity.EntityType;
import org.toadallyarmed.util.logger.Logger;
import org.toadallyarmed.util.rendering.AnimatedSprite;
import org.toadallyarmed.util.rendering.AnimatedStateSprite;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CoinFactory implements Disposable {
    private static final CoinFactory factoryInstance = new CoinFactory();

    private final Texture coinTexture;
    AnimationFactory coinAnimationFactory=new AnimationFactory(new AnimationConfig(
        0.12f, new Vector2(0.3f, 0.8f), new Vector2(0.3f, 0.3f), 6, 1, false
    )), specialCoinAnimationFactory=new AnimationFactory(new AnimationConfig(
        0.25f, new Vector2(0.15f, 0.15f), new Vector2(0.7f, 0.7f), 6, 1, false
    ));
    private final AnimatedStateSprite<CoinState> coinAnimatedStateSprite, specialCoinAnimatedStateSprite;

    private CoinFactory() {
        Logger.trace("Initializing CoinFactory");

        coinTexture  = new Texture("GameScreen/Coins/coin.png");
        coinAnimatedStateSprite = createAnimatedStateSprite(coinTexture);
        specialCoinAnimatedStateSprite = createSpecialAnimatedStateSprite(coinTexture);

        Logger.debug("Initialized CoinFactory successfully");
    }

    public static CoinFactory get() {
        return factoryInstance;
    }

    public Entity createCoin(Vector2 pos) { return createCoin(coinAnimatedStateSprite, pos); }
    public Entity createSpecialCoin(Vector2 pos) {return createCoin(specialCoinAnimatedStateSprite, pos);}

    @Override
    public void dispose() {
        Logger.trace("Disposing CoinFactory");
        coinTexture.dispose();
    }


    private Entity createCoin(AnimatedStateSprite<CoinState> animatedStateSprite, Vector2 pos) {
        Logger.trace("Creating Coin Entity in factory");
        WorldTransformComponent transform = new WorldTransformComponent(pos, new Vector2());
        CoinStateComponent CoinState = new CoinStateComponent();
        CoinRenderableComponent renderable =
            new CoinRenderableComponent(
                transform,
                CoinState,
                animatedStateSprite);
        return new Entity(EntityType.COIN)
            .put(TransformComponent.class, transform)
            .put(StateComponent.class, CoinState)
            .put(RenderableComponent.class, renderable);
    }

    @SuppressWarnings("ReassignedVariable")
    private AnimatedStateSprite<CoinState> createAnimatedStateSprite(Texture texture) {
        Map<CoinState, AnimatedSprite> animatedSprites=new HashMap<>();
        animatedSprites.put(CoinState.IDLE, coinAnimationFactory.Animation(texture, 0, 0, 5));
        animatedSprites.put(CoinState.NONEXISTENT, AnimatedSprite.empty());
        return new AnimatedStateSprite<>(animatedSprites);
    }

    @SuppressWarnings("ReassignedVariable")
    private AnimatedStateSprite<CoinState> createSpecialAnimatedStateSprite(Texture texture) {
        Map<CoinState, AnimatedSprite> animatedSprites = new HashMap<>();
        animatedSprites.put(CoinState.IDLE, specialCoinAnimationFactory.Animation(texture, 0, 0, 5));
        animatedSprites.put(CoinState.NONEXISTENT, AnimatedSprite.empty());
        return new AnimatedStateSprite<>(animatedSprites);
    }
}
