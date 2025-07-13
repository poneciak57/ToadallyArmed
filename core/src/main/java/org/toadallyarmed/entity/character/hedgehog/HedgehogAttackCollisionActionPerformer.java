package org.toadallyarmed.entity.character.hedgehog;

import org.toadallyarmed.entity.character.aliveentity.AliveEntityStateComponent;
import org.toadallyarmed.base.health.HealthComponent;
import org.toadallyarmed.base.action.BasicCollisionActionPayload;
import org.toadallyarmed.base.state.StateComponent;
import org.toadallyarmed.base.transform.TransformComponent;
import org.toadallyarmed.entity.character.frog.FrogState;
import org.toadallyarmed.util.action.ActionAdapter;
import org.toadallyarmed.util.action.PayloadExtractor;

import java.util.Optional;

public class HedgehogAttackCollisionActionPerformer extends ActionAdapter<HedgehogAttackCollisionActionPayload, BasicCollisionActionPayload> {
    static final PayloadExtractor<HedgehogAttackCollisionActionPayload, BasicCollisionActionPayload>
        EXTRACTOR = basicCollisionActionPayload -> {
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
        HedgehogStateComponent headgehogESC = (HedgehogStateComponent) stateComponent.get();
        @SuppressWarnings("unchecked")
        AliveEntityStateComponent<FrogState> frogESC = (AliveEntityStateComponent<FrogState>) otherStateComponent.get();

        return Optional.of(new HedgehogAttackCollisionActionPayload(
            frogESC.getIsAttackedStateMachine(),
            healthComponent.get(),
            headHogTimerComponent.get(),
            transformComponent.get(),
            headgehogESC.getGeneralStateMachine(),
            basicCollisionActionPayload.currentNanoTime()
        ));
    };

    public HedgehogAttackCollisionActionPerformer(int damage) {
        super(new HedgehogAttackCollisionActionImpl(damage), EXTRACTOR);
    }
}
