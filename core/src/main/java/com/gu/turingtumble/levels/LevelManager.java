package com.gu.turingtumble.levels;

import com.gu.turingtumble.utils.GameManager;
import com.gu.turingtumble.utils.GameState;

import java.util.HashSet;
import java.util.Set;

public class LevelManager {


    public static Level currentLevel;
    private static int currentLevelNumber;
    private static Set<Integer> unlockedLevels = new HashSet<>();

    static {
        unlockedLevels.add(1);
    }

    public static void loadLevel(int levelNumber) {
        GameManager.clearComponents();
        currentLevelNumber = levelNumber;

        switch (levelNumber) {
            case 1:
                currentLevel = new Level1();
                break;
            case 2:
                // currentLevel = new Level2();
                break;
            default:
                throw new IllegalArgumentException("Unknown level: " + levelNumber);
        }
        currentLevel.initialise();
    }

    public static Level getCurrentLevel() {
        return currentLevel;
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
        }
    }

}
