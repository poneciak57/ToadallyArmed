package org.toadallyarmed.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import org.toadallyarmed.component.BasicStateComponent;
import org.toadallyarmed.component.BasicStatefulRenderableComponent;
import org.toadallyarmed.component.WorldTransformComponent;
import org.toadallyarmed.state.BasicEntityState;
import org.toadallyarmed.component.interfaces.RenderableComponent;
import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.config.AnimationConfig;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.entity.EntityType;
import org.toadallyarmed.util.StateMachine;
import org.toadallyarmed.util.logger.Logger;
import org.toadallyarmed.util.rendering.AnimatedSprite;
import org.toadallyarmed.util.rendering.AnimatedStateSprite;

import java.util.HashMap;
import java.util.Map;

public class CoinFactory implements Disposable {
    private static final CoinFactory factoryInstance = new CoinFactory();

    private final Texture coinTexture;
    final AnimationFactory coinAnimationFactory=new AnimationFactory(new AnimationConfig(
        0.12f, new Vector2(0.3f, 0.8f), new Vector2(0.3f, 0.3f), 6, 1, false
    )), specialCoinAnimationFactory=new AnimationFactory(new AnimationConfig(
        0.10f, new Vector2(0.15f, 0.15f), new Vector2(0.7f, 0.7f), 6, 1, false
    ));
    private final AnimatedStateSprite<BasicEntityState> coinAnimatedStateSprite, specialCoinAnimatedStateSprite;

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


    private Entity createCoin(AnimatedStateSprite<BasicEntityState> animatedStateSprite, Vector2 pos) {
        Logger.trace("Creating Coin Entity in factory");
        WorldTransformComponent transform = new WorldTransformComponent(pos, new Vector2());
        StateMachine<BasicEntityState> stateMachine = new StateMachine<>(BasicEntityState.IDLE);
        stateMachine.addState(BasicEntityState.IDLE, BasicEntityState.IDLE, true);
        stateMachine.addState(BasicEntityState.NONEXISTENT, BasicEntityState.NONEXISTENT, false);
        BasicStateComponent<BasicEntityState> state = new BasicStateComponent<>(stateMachine);
        BasicStatefulRenderableComponent<BasicEntityState> renderable =
            new BasicStatefulRenderableComponent<>(
                transform,
                state,
                animatedStateSprite);
        return new Entity(EntityType.COIN)
            .put(TransformComponent.class, transform)
            .put(StateComponent.class, state)
            .put(RenderableComponent.class, renderable);
    }

    private AnimatedStateSprite<BasicEntityState> createAnimatedStateSprite(Texture texture) {
        Map<BasicEntityState, AnimatedSprite> animatedSprites=new HashMap<>();
        animatedSprites.put(BasicEntityState.IDLE, coinAnimationFactory.Animation(texture, 0, 0, 6));
        animatedSprites.put(BasicEntityState.NONEXISTENT, AnimatedSprite.empty());
        return new AnimatedStateSprite<>(animatedSprites);
    }

    private AnimatedStateSprite<BasicEntityState> createSpecialAnimatedStateSprite(Texture texture) {
        Map<BasicEntityState, AnimatedSprite> animatedSprites = new HashMap<>();
        animatedSprites.put(BasicEntityState.IDLE, specialCoinAnimationFactory.Animation(texture, 0, 0, 6));
        animatedSprites.put(BasicEntityState.NONEXISTENT, AnimatedSprite.empty());
        return new AnimatedStateSprite<>(animatedSprites);
    }
}
