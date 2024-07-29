package com.gu.turingtumble;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gu.turingtumble.utils.GameConstant;
import com.gu.turingtumble.game.ui.GameMainMenu;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.VisUI;
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
    }

    @Override
    public void resize(int width, int height) {
        getScreen().resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
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



