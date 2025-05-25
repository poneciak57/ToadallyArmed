package org.toadallyarmed.component.action.payload;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.component.AliveEntityStateComponent;
import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.entity.Entity;
import org.toadallyarmed.state.FrogState;
import org.toadallyarmed.util.StateMachine;
import org.toadallyarmed.util.action.PayloadExtractor;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

public record FrogAttackCollisionActionPayload(
    Vector2 pos,
    float currentNano,
    StateMachine<FrogState> stateMachine,
    ConcurrentLinkedQueue<Entity> entities
) {
    public static final PayloadExtractor<FrogAttackCollisionActionPayload, BasicCollisionActionPayload> EXTRACTOR =
        basicCollisionActionPayload -> {
            var pos = basicCollisionActionPayload.entity().get(TransformComponent.class);
            var stateComponent = basicCollisionActionPayload.entity().get(StateComponent.class);
            if (stateComponent.isEmpty()) return Optional.empty();
            if (pos.isEmpty()) return Optional.empty();

            /// If some entity requires this payload extractor it should be frog so it should have
            /// StateMachine<FrogState> compoennt if not it is critical error
            @SuppressWarnings("unchecked")
            AliveEntityStateComponent<FrogState> frogESC = (AliveEntityStateComponent<FrogState>) stateComponent.get();
            return Optional.of(
                new FrogAttackCollisionActionPayload(
                    pos.get().getAdvancedPosition(basicCollisionActionPayload.currentNanoTime()),
                    basicCollisionActionPayload.currentNanoTime(),
                    frogESC.getGeneralStateMachine(),
                    basicCollisionActionPayload.gameState().getEntities()
                )
            );
        };
}
