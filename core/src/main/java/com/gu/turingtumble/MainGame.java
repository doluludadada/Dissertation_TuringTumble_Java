package com.gu.turingtumble;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gu.turingtumble.utils.GameBoard;
import com.gu.turingtumble.utils.GameConstant;
import com.gu.turingtumble.utils.GameUI;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.VisUI;


public class MainGame extends Game {
    private GameBoard gameBoard;
    private GameUI gameUI;
    private Stage uiStage;
    private Stage gameStage;


    @Override
    public void create() {
        if (!VisUI.isLoaded()) VisUI.load();


//        create UI window
        uiStage = new Stage(new ScreenViewport());
        uiStage.getViewport().setWorldWidth(GameConstant.UI_WIDTH.get());
        uiStage.getViewport().setWorldHeight(GameConstant.WINDOW_HEIGHT.get());

//        create game window
        gameStage = new Stage(new ScreenViewport());
        gameStage.getViewport().setWorldWidth(GameConstant.GAME_WIDTH.get());
        gameStage.getViewport().setWorldHeight(GameConstant.WINDOW_HEIGHT.get());


        Gdx.input.setInputProcessor(uiStage);

        gameBoard = GameBoard.getInstance();
        gameUI = new GameUI(uiStage);

        setScreen(gameBoard);
        gameBoard.show();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render game board
        gameStage.getViewport().apply();
        gameBoard.render(Gdx.graphics.getDeltaTime());

        // Render UI
        uiStage.getViewport().apply();
        uiStage.act(Gdx.graphics.getDeltaTime());
        uiStage.draw();

    }

    @Override
    public void resize(int width, int height) {

        gameBoard.resize(GameConstant.GAME_WIDTH.get(), height);
        gameUI.resize(GameConstant.UI_WIDTH.get(), height);

    }

    @Override
    public void dispose() {
        super.dispose();
        gameBoard.dispose();
        uiStage.dispose();
        gameStage.dispose();
        VisUI.dispose();
    }
}



