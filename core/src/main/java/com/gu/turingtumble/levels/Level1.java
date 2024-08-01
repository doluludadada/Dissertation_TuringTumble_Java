package com.gu.turingtumble.levels;

import com.badlogic.gdx.math.Vector2;
import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.utils.GameManager;

public class Level1 implements Level {
    @Override
    public void initialize() {
        System.out.println("level1 initialized");
        putComponent();
    }

    private void putComponent() {
        System.out.println("test message putComponent");
        // 固定生成的 Ramp
        GameManager.setSelectedComponent("Ramp");
        GameManager.addComponent(0, 4);
        GameManager.addComponent(2, 4);
        GameManager.addComponent(4, 4);
        GameManager.addComponent(6, 4);
        GameManager.addComponent(8, 4);

        // 鏡像 Ramp
//        GameManager.setIsMirrorSelected(true);
        GameManager.setSelectedComponent("MirrorRamp");
        GameManager.addComponent(1, 5);
//        GameManager.setIsMirrorSelected(false);

    }

    @Override
    public void reset(MainGame game) {
        GameManager.resetLevel();
    }

    @Override
    public void onComplete() {
        LevelManager.unlockLevel(2);
    }
}
