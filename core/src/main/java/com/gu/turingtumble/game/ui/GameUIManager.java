package com.gu.turingtumble.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gu.turingtumble.utils.GameConstant;
import com.gu.turingtumble.utils.GameManager;
import com.kotcrab.vis.ui.VisUI;

import com.gu.turingtumble.MainGame;


/**
 * Manages all UI stages and rendering in the game.
 */

public class GameUIManager {
    private Stage uiStage;
    private Stage gameStage;
    private MainGame game;
    private GameUI gameUI;


    /**
     * Constructor for GameUIManager. Initializes the stages and loads VisUI.
     *
     * @param game The main game instance.
     */

    public GameUIManager(MainGame game) {
        this.game = game;
        initialiseStages();
        initialiseVisUI();
        GameManager.initialise(game);
    }


    private void initialiseVisUI() {
        if (!VisUI.isLoaded()) {
            VisUI.load();
        }
    }


    private void initialiseStages() {
        uiStage = new Stage(new ScreenViewport());
        uiStage.getViewport().setWorldWidth(GameConstant.UI_WIDTH.get());
        uiStage.getViewport().setWorldHeight(GameConstant.WINDOW_HEIGHT.get());

        gameStage = new Stage(new ScreenViewport());
        gameStage.getViewport().setWorldWidth(GameConstant.GAME_WIDTH.get());
        gameStage.getViewport().setWorldHeight(GameConstant.WINDOW_HEIGHT.get());

        Gdx.input.setInputProcessor(uiStage);
    }


    /**
     * Renders both the UI and game stages.
     */
    public void render() {
        uiStage.act(Gdx.graphics.getDeltaTime());
        uiStage.draw();
        gameStage.act(Gdx.graphics.getDeltaTime());
        gameStage.draw();
    }

    /**
     * Resizes both the UI and game stages.
     *
     * @param width  The new width.
     * @param height The new height.
     */
    public void resize(int width, int height) {
        uiStage.getViewport().update(width, height, true);
        gameStage.getViewport().update(width, height, true);
    }

    /**
     * Disposes of both the UI and game stages.
     */
    public void dispose() {
        uiStage.dispose();
        gameStage.dispose();
        VisUI.dispose();
    }

    /**
     * Clears all actors from both the UI and game stages.
     */
    public void clear() {
        uiStage.clear();
        gameStage.clear();
    }

    public void showGameUI() {
        clear();
        gameUI = new GameUI(game, uiStage);
    }

    public void showMainMenu() {
        clear();
        new GameMainMenu(game, uiStage);
    }

    public void showLevelSelectMenu() {
        clear();
        new GameLevelSelectMenu(game, uiStage);
    }

    public Stage getUiStage() {
        return uiStage;
    }

    public Stage getGameStage() {
        return gameStage;
    }

    public GameUI getGameUI() {
        return gameUI;
    }

    public void updateUI() {
        if (gameUI != null) {
            gameUI.updateUI();
        }
    }
}
