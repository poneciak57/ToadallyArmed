package org.toadallyarmed.component.action.payload;

import org.toadallyarmed.component.AliveEntityStateComponent;
import org.toadallyarmed.component.HeadgehogAttackTimerComponent;
import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.state.HedgehogState;
import org.toadallyarmed.util.StateMachine;
import org.toadallyarmed.util.action.PayloadExtractor;

import java.util.Optional;

public record HeadgehogAttackResetActionPayload(
    HeadgehogAttackTimerComponent timer,
    StateMachine<HedgehogState> stateMachine,
    TransformComponent transformComponent
) {
    public static final PayloadExtractor<HeadgehogAttackResetActionPayload, BasicActionPayload> EXTRACTOR =
        basicActionPayload -> {
            var transformComponent = basicActionPayload.entity().get(TransformComponent.class);
            if (transformComponent.isEmpty()) return Optional.empty();

            var stateComponent = basicActionPayload.entity().get(StateComponent.class);
            if (stateComponent.isEmpty()) return Optional.empty();

            var timer = basicActionPayload.entity().get(HeadgehogAttackTimerComponent.class);
            if (timer.isEmpty()) return Optional.empty();

            /// If some entity requires this payload extractor it should be headgehog so it should have
            /// StateMachine<Headgehog> compoennt if not it is critical error
            @SuppressWarnings("unchecked")
            AliveEntityStateComponent<HedgehogState> headgehogESC = (AliveEntityStateComponent<HedgehogState>) stateComponent.get();

            return Optional.of(new HeadgehogAttackResetActionPayload(
                timer.get(),
                headgehogESC.getGeneralStateMachine(),
                transformComponent.get()
            ));
        };
}
