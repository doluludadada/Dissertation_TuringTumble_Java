package com.gu.turingtumble.utils;

import java.util.*;

public class GameState {
    private static List<Integer> requireGoal = new ArrayList<>();
    private static List<Integer> currentState = new ArrayList<>();

    public GameState() {
        // Initialize with empty lists; specific levels will set the appropriate size and values.
    }

    public List<Integer> getRequireGoal() {
        return requireGoal;
    }

    public void setRequireGoal(List<Integer> requireGoal) {
        GameState.requireGoal = new ArrayList<>(requireGoal);
        resetCurrentState(requireGoal.size());
    }

    public List<Integer> getCurrentState() {
        return currentState;
    }

    public void setCurrentState(List<Integer> currentState) {
        GameState.currentState = new ArrayList<>(currentState);
    }

    public void addCurrentState(int state) {
        for (int i = 0; i < currentState.size(); i++) {
            if (currentState.get(i) == -1) {
                currentState.set(i, state);
                break;
            }
        }
    }

    public static void resetCurrentState(int size) {
        currentState = new ArrayList<>(Collections.nCopies(size, -1));
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
