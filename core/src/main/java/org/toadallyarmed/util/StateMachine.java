package org.toadallyarmed.util;

import org.toadallyarmed.util.logger.Logger;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class StateMachine<State extends Enum<State>> {
    private record TmpState<S>(
        S state,
        Runnable action
    ) {}

    private static class StateNode<State extends Enum<State>> {
        public StateNode(State next, Runnable afterStateAction, boolean allowTmpNext) {
            this.next = next;
            this.afterStateAction = afterStateAction;
            this.allowTmpNext = allowTmpNext;
        }

        public State getNextState() {
            return next;
        }

        public void setTmpNextState(State from, State to, Runnable afterStateAction) {
            if (!allowTmpNext) return;
            var old = tmpNext.getAndSet(new TmpState<>(to, afterStateAction));
            if (old != null)
                Logger.warn("Temporary next state " + from + " overwritten before the consumption");
        }

        public Optional<TmpState<State>> useTmpNextIfAvailable() {
            if (!allowTmpNext) return Optional.empty();
            TmpState<State> tmpState = tmpNext.getAndSet(null);
            if (tmpState != null) return Optional.of(tmpState);
            else return Optional.empty();
        }

        void performAfterStateActionIfPresent() {
            if (afterStateAction != null) afterStateAction.run();
        }

        private final State next;
        private final boolean allowTmpNext;
        private final AtomicReference<TmpState<State>> tmpNext = new AtomicReference<>(null);
        private final Runnable afterStateAction;
    }

    private volatile State curState;
    private final AtomicReference<Runnable> tmpRunnable = new AtomicReference<>(null);
    private final Map<State, StateNode<State>> states;

    public StateMachine(State initialState) {
        this.curState = initialState;
        states = new ConcurrentHashMap<>();
    }

    public State getCurState() {
        return curState;
    }

    public void advanceState() {
        Logger.trace("advanceState()");
        final StateNode<State> curStateNode = getStateNode(curState);
        curStateNode.performAfterStateActionIfPresent();
        var tmpRunnable = this.tmpRunnable.getAndSet(null);
        if (tmpRunnable != null) tmpRunnable.run();
        curStateNode.useTmpNextIfAvailable().ifPresentOrElse(
            tmpState -> {
                curState = tmpState.state();
                this.tmpRunnable.set(tmpState.action());
            },
            () -> curState = curStateNode.getNextState()
        );
    }

    public StateMachine<State> addState(State state, State next, boolean allowTmpNext) {
        return addState(state, next, null, allowTmpNext);
    }

    public StateMachine<State> addState(State state, State next, Runnable afterStateAction, boolean allowTmpNext) {
        StateNode<State> newStateNode = new StateNode<>(next, afterStateAction, allowTmpNext);
        if (states.putIfAbsent(state, newStateNode) != null) {
            Logger.error("State " + state + " already exists. Overwriting!!!");
            states.put(state, newStateNode);
        }
        return this;
    }

    public void setNextTmpState(State from, State to, Runnable afterStateAction) {
        Logger.trace("setNextTmpStateFrom(" + from + ", " + to + ")");
        StateNode<State> fromStateNode = getStateNode(from);
        fromStateNode.setTmpNextState(from, to, afterStateAction);
    }

    public void setNextTmpState(State from, State to) {
        setNextTmpState(from, to, null);
    }

    public void setNextTmpState(State newState) {
        setNextTmpState(getCurState(), newState);
    }

    private StateNode<State> getStateNode(State state) {
        var stateNode = states.get(state);
        if (stateNode == null)
            Logger.error("State " + state + " does not exist in the state machine!");
        return stateNode;
    }
}
