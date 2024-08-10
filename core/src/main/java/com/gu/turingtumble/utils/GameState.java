package com.gu.turingtumble.utils;

import java.util.*;

public class GameState {
    private static GameState instance;
    private List<Integer> requireGoal;
    private List<Integer> currentState;
    private int allowedBallStopper; // -1: both sides can be used, 0: only use left side, 1: only use right side



    private GameState() {
        this.requireGoal = new ArrayList<>();
        this.currentState = new ArrayList<>();
        this.allowedBallStopper = -1;
    }

    public static synchronized GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    public List<Integer> getRequireGoal() {
        return new ArrayList<>(requireGoal);
    }

    public void setRequireGoal(List<Integer> requireGoal) {
        this.requireGoal = new ArrayList<>(requireGoal);
        resetCurrentState(requireGoal.size());
    }

    public List<Integer> getCurrentState() {
        return new ArrayList<>(currentState);
    }

    public void setCurrentState(List<Integer> currentState) {
        this.currentState = new ArrayList<>(currentState);
    }

    public void addCurrentOutput(int output) {
        for (int i = 0; i < currentState.size(); i++) {
            if (currentState.get(i) == -1) {
                currentState.set(i, output);
                break;
            }
        }
    }

    public void resetCurrentState(int size) {
        this.currentState = new ArrayList<>(Collections.nCopies(size, -1));
    }

    public boolean isComplete() {
        return requireGoal.equals(currentState);
    }

    public boolean checkOutput() {
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

    public int getAllowedBallStopper() {
        return allowedBallStopper;
    }

    public void setAllowedBallStopper(int allowedNum) {
        this.allowedBallStopper = allowedNum;
    }



    public void reset() {
        this.requireGoal.clear();
        this.currentState.clear();
        this.allowedBallStopper = -1;
    }
}
