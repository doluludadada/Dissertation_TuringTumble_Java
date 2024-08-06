package com.gu.turingtumble;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.gu.turingtumble.game.ui.GameUIManager;

import com.gu.turingtumble.utils.GameManager;

public class MainGame extends Game {
    public static GameUIManager uiManager;

    @Override
    public void create() {
        uiManager = new GameUIManager(this);
        uiManager.showMainMenu();                                                                                       // Set screen to MainMenu
        System.out.println("Current Delta Time: " + Gdx.graphics.getDeltaTime());       //for test
    }

    @Override
    public void render() {
//        clear
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
        uiManager.render();
    }

    @Override
    public void resize(int width, int height) {
        uiManager.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        if (uiManager != null) {
            uiManager.dispose();
        }
    }

    public static GameUIManager getUiManager() {
        return uiManager;
    }

    public void restartGame() {
        GameManager.clearAll();
        MainGame.getUiManager().showMainMenu();
    }

    public void restartApplication() {
        Gdx.app.exit();
    }
}
