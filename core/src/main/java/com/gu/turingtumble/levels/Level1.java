package com.gu.turingtumble.levels;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.game.ui.GameHints;
import com.gu.turingtumble.utils.GameManager;
import com.gu.turingtumble.utils.GameState;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Level1 extends Level {



    public Level1() {
        super();
        Map<String, Integer> limits = new HashMap<>();
        limits.put("Ramp", 4);
        setComponentLimits(limits);
        GameHints.getInstance();
    }

    @Override
    public void initialise() {
        GameHints.getInstance().showNextHint();
        super.initialise();

    }


    @Override
    public void setAllowedBallStopper() {
        GameState.getInstance().setAllowedBallStopper(0);
    }

    @Override
    public void basicComponent() {
        GameManager.setSelectedComponent("Ramp");
        GameManager.addComponent(0, 4);
        GameManager.addComponent(2, 4);
        GameManager.addComponent(4, 4);
        GameManager.addComponent(6, 4);
        GameManager.addComponent(8, 4);

        GameManager.setSelectedComponent("MirrorRamp");
        GameManager.addComponent(1, 5);
    }

    @Override
    public void setVictoryCondition() {
        GameManager.getGameState().setRequireGoal(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0));
    }
}

