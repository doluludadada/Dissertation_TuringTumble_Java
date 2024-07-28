package com.gu.turingtumble.levels;

public class LevelManager {
    private static Level currentLevel;

    public static void loadLevel(int levelNumber) {
        switch (levelNumber) {
            case 1:
                currentLevel = new Level1();
                break;
//            case 2:
//                currentLevel = new Level2();
//                break;
            // Add cases for levels 3 to 10
            case 3:
                // currentLevel = new Level3();
                break;
            case 4:
                // currentLevel = new Level4();
                break;
            case 5:
                // currentLevel = new Level5();
                break;
            case 6:
                // currentLevel = new Level6();
                break;
            case 7:
                // currentLevel = new Level7();
                break;
            case 8:
                // currentLevel = new Level8();
                break;
            case 9:
                // currentLevel = new Level9();
                break;
            case 10:
                // currentLevel = new Level10();
                break;
            default:
                throw new IllegalArgumentException("Unknown level: " + levelNumber);
        }
        currentLevel.initialize();
    }

    public static Level getCurrentLevel() {
        return currentLevel;
    }
}
