package com.gu.turingtumble.utils;

import java.util.*;

public class GameState {
    private static List<Integer> requireGoal;
    private static List<Integer> currentState;


    public GameState() {
        requireGoal = new ArrayList<>();
        currentState = new ArrayList<>(Collections.nCopies(8, -1));
    }

    public List<Integer> getRequireGoal() {
        return requireGoal;
    }

    public void setRequireGoal(List<Integer> requireGoal) {
        GameState.requireGoal = requireGoal;
    }

    public List<Integer> getCurrentState() {
        return currentState;
    }

    public void setCurrentState(List<Integer> currentState) {
        GameState.currentState = currentState;
    }

    public void addCurrentState(int state) {
        for (int i = 0; i < currentState.size(); i++) {
            if (currentState.get(i) == -1) {
                currentState.set(i, state);
                break;
            }
        }
    }

    public static void resetCurrentState() {
        currentState = new ArrayList<>(Collections.nCopies(8, -1));
    }

    public static boolean isComplete() {
        return requireGoal.equals(currentState);
    }

    public static boolean checkOutput() {
        for (int i = 0; i < requireGoal.size(); i++) {
            if (currentState.get(i) == -1) {
                continue;
            }
            if (!Objects.equals(requireGoal.get(i), currentState.get(i))) {
                return true;
            }
        }
        return false;
    }
}
