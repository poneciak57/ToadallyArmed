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
        final StateNode<State> curStateNode = getStateNode(curState);
        if (curStateNode.tmpNext != null) {
            curState = curStateNode.tmpNext;
            curStateNode.tmpNext = null;
        } else {
            curState = curStateNode.next;
        }
    }

    public void setNextStateFrom(State from, State to) {
        StateNode<State> fromStateNode = getStateNode(from);
        fromStateNode.next = to;
    }

    public void setNextTmpStateFrom(State from, State to) {
        Logger.trace("setNextTmpStateFrom(" + from + ", " + to + ")");
        StateNode<State> fromStateNode = getStateNode(from);
        fromStateNode.tmpNext = to;
    }

    private StateNode<State> getStateNode(State state) {
        states.putIfAbsent(state, new StateNode<>(state));
        return states.get(state);
    }
}
