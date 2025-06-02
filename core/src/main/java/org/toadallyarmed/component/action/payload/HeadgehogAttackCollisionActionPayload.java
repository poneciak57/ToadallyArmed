package org.toadallyarmed.component.action.payload;

import org.toadallyarmed.component.AliveEntityStateComponent;
import org.toadallyarmed.component.HeadgehogAttackTimerComponent;
import org.toadallyarmed.component.HealthComponent;
import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.state.BooleanState;
import org.toadallyarmed.state.FrogState;
import org.toadallyarmed.state.HedgehogState;
import org.toadallyarmed.util.StateMachine;
import org.toadallyarmed.util.action.PayloadExtractor;

import java.util.Optional;

public record HeadgehogAttackCollisionActionPayload(
    StateMachine<BooleanState> frogIsAttackedStateMachine,
    HealthComponent frogHealthComponent,
    HeadgehogAttackTimerComponent headgehogAttackTimerComponent,
    TransformComponent transformComponent,
    StateMachine<HedgehogState> stateMachine,
    float currentNanoTime
) {
    public static final PayloadExtractor<HeadgehogAttackCollisionActionPayload, BasicCollisionActionPayload> EXTRACTOR =
        basicCollisionActionPayload -> {
            var stateComponent = basicCollisionActionPayload.entity().get(StateComponent.class);
            if (stateComponent.isEmpty()) return Optional.empty();

            var otherStateComponent = basicCollisionActionPayload.other().get(StateComponent.class);
            if (otherStateComponent.isEmpty()) return Optional.empty();

            var transformComponent = basicCollisionActionPayload.entity().get(TransformComponent.class);
            if (transformComponent.isEmpty()) return Optional.empty();

            var healthComponent = basicCollisionActionPayload.other().get(HealthComponent.class);
            if (healthComponent.isEmpty()) return Optional.empty();

            var headHogTimerComponent = basicCollisionActionPayload.entity().get(HeadgehogAttackTimerComponent.class);
            if (headHogTimerComponent.isEmpty()) return Optional.empty();

            /// If some entity requires this payload extractor it should be headgehog so it should have
            /// StateMachine<Headgehog> compoennt if not it is critical error
            @SuppressWarnings("unchecked")
            AliveEntityStateComponent<HedgehogState> headgehogESC = (AliveEntityStateComponent<HedgehogState>) stateComponent.get();
            @SuppressWarnings("unchecked")
            AliveEntityStateComponent<FrogState> frogESC = (AliveEntityStateComponent<FrogState>) otherStateComponent.get();

            return Optional.of(new HeadgehogAttackCollisionActionPayload(
                frogESC.getIsAttackedStateMachine(),
                healthComponent.get(),
                headHogTimerComponent.get(),
                transformComponent.get(),
                headgehogESC.getGeneralStateMachine(),
                basicCollisionActionPayload.currentNanoTime()
            ));
        };
}
