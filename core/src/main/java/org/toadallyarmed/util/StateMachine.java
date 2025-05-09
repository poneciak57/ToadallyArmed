package org.toadallyarmed.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class StateMachine<State extends Enum<State>> {
    private static class StateNode<State extends Enum<State>> {
        StateNode(State next) {
            this.next = next;
        }

        State next;
        Optional<State> tmpNext;
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
        final StateNode<State> curStateNode = getStateNode(curState);
        if (curStateNode.tmpNext.isPresent()) {
            curState = curStateNode.tmpNext.get();
            curStateNode.tmpNext = Optional.empty();
        } else {
            curState = curStateNode.next;
        }
    }

    public void setNextStateFrom(State from, State to) {
        StateNode<State> fromStateNode = getStateNode(from);
        fromStateNode.next = to;
    }

    public void setNextTmpStateFrom(State from, State to) {
        StateNode<State> fromStateNode = getStateNode(from);
        fromStateNode.next = to;
    }

    private StateNode<State> getStateNode(State state) {
        return states.getOrDefault(state, new StateNode<>(state));
    }
}
