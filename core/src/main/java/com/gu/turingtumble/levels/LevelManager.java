package com.gu.turingtumble.levels;

import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.game.ui.GameUIManager;
import com.gu.turingtumble.utils.GameConstant;
import com.gu.turingtumble.utils.GameManager;
import com.gu.turingtumble.utils.GameState;

import java.util.HashSet;
import java.util.Set;

public class LevelManager {


    private static Level currentLevel;
    private static int currentLevelNumber;
    private static final Set<Integer> unlockedLevels = new HashSet<>();


    static {
        unlockedLevels.add(1);
        if (GameConstant.TEST_MODE.get() == 1) {
            unlockAllLevels();
        }
    }

    public static void loadLevel(int levelNumber) {

        if (currentLevel != null) {
            currentLevel.dispose();
        }

        currentLevelNumber = levelNumber;

        currentLevel = switch (levelNumber) {
            case 1 -> new Level1();
            case 2 -> new Level2();
            case 3 -> new Level3();
            case 4 -> new Level4();
            case 5 -> new Level5();
            case 6 -> new Level6();
            case 7 -> new Level7();
            case 8 -> new Level8();
            case 9 -> new Level9();
            case 10 -> new Level10();
            default -> throw new IllegalArgumentException("Unknown level: " + levelNumber);
        };

        currentLevel.initialise();
    }

    public static Level getCurrentLevel() {
        return currentLevel;
    }

    public static void setCurrentLevelNumber(int currentLevelNumber) {
        LevelManager.currentLevelNumber = currentLevelNumber;
    }

    public static boolean isLevelUnlocked(int levelNumber) {
        return unlockedLevels.contains(levelNumber);
    }

    public static void unlockLevel(int levelNumber) {
        unlockedLevels.add(levelNumber);
    }

    public static int getCurrentLevelNumber() {
        return currentLevelNumber;
    }

    public static void checkLevelCompletion() {
        if (currentLevel != null && currentLevel.isComplete()) {
            unlockLevel(currentLevelNumber + 1);
            MainGame.getUiManager().getGameUI().showLevelCompleteDialog();
        }
    }

    private static void unlockAllLevels() {
        for (int i = 1; i <= 10; i++) {
            unlockedLevels.add(i);
        }
    }


}
