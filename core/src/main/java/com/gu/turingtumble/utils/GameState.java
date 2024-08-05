package com.gu.turingtumble.utils;

import java.util.*;

public class GameState {
    private int goalBlueBall;
    private int goalRedBall;



    public GameState() {
        this.goalBlueBall = 0;
        this.goalRedBall = 0;
    }

    public int getGoalBlueBall() {
        return goalBlueBall;
    }

    public void setGoalBlueBall(int goalBlueBall) {
        this.goalBlueBall = goalBlueBall;
    }

    public int getGoalRedBall() {
        return goalRedBall;
    }

    public void setGoalRedBall(int goalRedBall) {
        this.goalRedBall = goalRedBall;
    }

    public void adjustBlueBallNum() {
        if (this.goalBlueBall > 0) {
            setGoalBlueBall(this.goalBlueBall - 1);
        }

    }

    public void adjustRedBallNum() {
        setGoalRedBall(this.goalRedBall - 1);
    }


    public void reset() {
        this.goalBlueBall = 0;
        this.goalRedBall = 0;
    }



}
