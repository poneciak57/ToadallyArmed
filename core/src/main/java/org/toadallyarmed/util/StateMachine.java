package org.toadallyarmed.util;

import org.toadallyarmed.util.logger.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class StateMachine<State extends Enum<State>> {
    private static class StateNode<State extends Enum<State>> {
        StateNode(State next, Runnable afterStateAction) {
            this.next = next;
            this.afterStateAction = afterStateAction;
        }

        final State next;
        final AtomicReference<State> tmpNext = new AtomicReference<>(null);

        final Runnable afterStateAction;
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
        if (curStateNode.afterStateAction != null) curStateNode.afterStateAction.run();
        var tmpNext = curStateNode.tmpNext.getAndSet(null);
        if (tmpNext != null) curState = tmpNext;
        else curState = curStateNode.next;
    }

    public void addState(State state, State next) {
        addState(state, next, null);
    }

    public void addState(State state, State next, Runnable afterStateAction) {
        StateNode<State> newStateNode = new StateNode<>(next, afterStateAction);
        if (states.putIfAbsent(state, newStateNode) != null) {
            Logger.error("State " + state + " already exists. Overwriting!!!");
            states.put(state, newStateNode);
        }
    }

    public void setNextTmpStateFrom(State from, State to) {
        Logger.trace("setNextTmpStateFrom(" + from + ", " + to + ")");
        StateNode<State> fromStateNode = getStateNode(from);
        var old = fromStateNode.tmpNext.getAndSet(to);
        if (old != null)
            Logger.warn("Temporary next state " + from + " overwritten before the consumption");
    }

    public void setNextTmpState(State newState) {
        setNextTmpStateFrom(getCurState(), newState);
    }

    private StateNode<State> getStateNode(State state) {
        var stateNode = states.get(state);
        if (stateNode == null)
            Logger.error("State " + state + " does not exist in the state machine!");
        return stateNode;
    }
}
