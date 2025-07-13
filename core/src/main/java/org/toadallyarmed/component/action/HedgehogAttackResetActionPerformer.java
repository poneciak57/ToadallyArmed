package org.toadallyarmed.component.action;

import com.badlogic.gdx.math.Vector2;
import org.toadallyarmed.component.HeadgehogAttackTimerComponent;
import org.toadallyarmed.component.HedgehogStateComponent;
import org.toadallyarmed.component.action.payload.BasicActionPayload;
import org.toadallyarmed.component.interfaces.StateComponent;
import org.toadallyarmed.component.interfaces.TransformComponent;
import org.toadallyarmed.util.action.ActionAdapter;
import org.toadallyarmed.util.action.PayloadExtractor;

import java.util.Optional;

public class HedgehogAttackResetActionPerformer extends ActionAdapter<HedgehogAttackResetActionPayload, BasicActionPayload> {
    private static final PayloadExtractor<HedgehogAttackResetActionPayload, BasicActionPayload>
        EXTRACTOR = basicActionPayload -> {
        var transformComponent = basicActionPayload.entity().get(TransformComponent.class);
        if (transformComponent.isEmpty()) return Optional.empty();

        var stateComponent = basicActionPayload.entity().get(StateComponent.class);
        if (stateComponent.isEmpty()) return Optional.empty();

        var timer = basicActionPayload.entity().get(HeadgehogAttackTimerComponent.class);
        if (timer.isEmpty()) return Optional.empty();

        /// If some entity requires this payload extractor it should be headgehog so it should have
        /// StateMachine<Headgehog> compoennt if not it is critical error
        @SuppressWarnings("unchecked")
        HedgehogStateComponent headgehogESC = (HedgehogStateComponent) stateComponent.get();

        return Optional.of(new HedgehogAttackResetActionPayload(
            timer.get(),
            headgehogESC.getGeneralStateMachine(),
            transformComponent.get()
        ));
    };

    public HedgehogAttackResetActionPerformer(long threshold, Vector2 velocity) {
        super(new HedgehogAttackResetActionImpl(threshold, velocity), EXTRACTOR);
    }
}
