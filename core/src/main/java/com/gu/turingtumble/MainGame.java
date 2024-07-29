package com.gu.turingtumble;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.gu.turingtumble.game.ui.GameMainMenu;
import com.gu.turingtumble.game.ui.GameUIManager;

public class MainGame extends Game {

    private GameUIManager uiManager;


    @Override
    public void create() {
        uiManager = new GameUIManager(this);

        // Set screen to MainMenu
        setScreen(new GameMainMenu(this));
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



