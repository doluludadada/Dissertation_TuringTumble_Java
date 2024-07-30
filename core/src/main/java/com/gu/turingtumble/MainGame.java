package com.gu.turingtumble;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.gu.turingtumble.game.ui.GameUIManager;
import com.gu.turingtumble.utils.GameManager;

public class MainGame extends Game {

    private GameUIManager uiManager;


    @Override
    public void create() {
        uiManager = new GameUIManager(this);

        GameManager.initialise();
        // Set screen to MainMenu
        uiManager.showMainMenu();
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
        uiManager.dispose();
    }

    public void removeScreen(Screen screen) {
        if (screen != null) {
            screen.hide();
            screen.dispose();
        }
        uiManager.clear();
    }

    public GameUIManager getUiManager() {
        return uiManager;
    }
}



