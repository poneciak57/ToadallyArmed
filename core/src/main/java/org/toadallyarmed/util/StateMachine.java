package org.toadallyarmed.util;

import org.toadallyarmed.util.logger.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StateMachine<State extends Enum<State>> {
    private static class StateNode<State extends Enum<State>> {
        StateNode(State next) {
            this.next = next;
        }

        State next;
        State tmpNext = null;

        Runnable afterStateAction = null;
    }

    State curState;
    Map<State, StateNode<State>> states;

    public StateMachine(State initialState) {
        this.curState = initialState;
        states = new HashMap<>();
    }

    public State getCurState() {
        return curState;
    }

    public void advanceState() {
        Logger.trace("advanceState()");
        synchronized (this) {
            final StateNode<State> curStateNode = getStateNode(curState);
            if (curStateNode.afterStateAction != null)
                curStateNode.afterStateAction.run();
            if (curStateNode.tmpNext != null) {
                curState = curStateNode.tmpNext;
                curStateNode.tmpNext = null;
            } else {
                curState = curStateNode.next;
            }
        }
    }

    public void setNextStateFrom(State from, State to) {
        synchronized (this) {
            StateNode<State> fromStateNode = getStateNode(from);
            fromStateNode.next = to;
        }
    }

    public void setNextTmpStateFrom(State from, State to) {
        Logger.trace("setNextTmpStateFrom(" + from + ", " + to + ")");
        synchronized (this) {
            StateNode<State> fromStateNode = getStateNode(from);
            fromStateNode.tmpNext = to;
        }
    }

    public void setAfterStateAction(State state, Runnable action) {
        synchronized (this) {
            StateNode<State> stateNode = getStateNode(state);
            stateNode.afterStateAction = action;
        }
    }

    private StateNode<State> getStateNode(State state) {
        synchronized (this) {
            states.putIfAbsent(state, new StateNode<>(state));
            return states.get(state);
        }
    }
}
