package com.gu.turingtumble.levels;

import com.badlogic.gdx.math.Vector2;
import com.gu.turingtumble.utils.GameManager;

public class Level1 implements Level {
    @Override
    public void initialize() {

        GameManager.setSelectedComponent("Ramp");
        GameManager.addComponent(new Vector2(0, 4));
        GameManager.addComponent(new Vector2(2, 4));
        GameManager.addComponent(new Vector2(4, 4));
        GameManager.addComponent(new Vector2(6, 4));
        GameManager.addComponent(new Vector2(8, 4));

        GameManager.setSelectedComponent("Ramp");
        GameManager.setIsMirrorSelected(true);
        GameManager.addComponent(new Vector2(1, 5));
        GameManager.setIsMirrorSelected(false); // 重設 Mirror 模式
    }



    @Override
    public void reset() {
        initialize();
    }
}
