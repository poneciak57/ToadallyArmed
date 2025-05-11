package org.toadallyarmed.util;

import org.toadallyarmed.util.logger.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class StateMachine<State extends Enum<State>> {
    private static class StateNode<State extends Enum<State>> {
        StateNode(State next) {
            this.next = next;
        }

        final State next;
        final AtomicReference<State> tmpNext = new AtomicReference<>(null);
    }

    private volatile State curState;
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
        var tmpNext = curStateNode.tmpNext.getAndSet(null);
        if (tmpNext != null) curState = tmpNext;
        else curState = curStateNode.next;
    }

    public void setNextStateFrom(State from, State to) {
        StateNode<State> newStateNode = new StateNode<>(to);
        if (states.putIfAbsent(from, newStateNode) != null) {
            Logger.error("State " + from + " already exists. Overwriting!!!");
            states.put(from, newStateNode);
        };
    }

    public void setNextTmpStateFrom(State from, State to) {
        Logger.trace("setNextTmpStateFrom(" + from + ", " + to + ")");
        StateNode<State> fromStateNode = getStateNode(from);
        var old = fromStateNode.tmpNext.getAndSet(to);
        if (old != null)
            Logger.warn("Temporary next state " + from + " overwritten before the consumption");
    }

    private StateNode<State> getStateNode(State state) {
        states.putIfAbsent(state, new StateNode<>(state));
        return states.get(state);
    }
}
