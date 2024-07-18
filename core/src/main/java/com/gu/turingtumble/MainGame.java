package com.gu.turingtumble;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gu.turingtumble.utils.GameBoard;


public class MainGame extends Game {
    public SpriteBatch batch;
    private GameBoard gameBoard;

    @Override
    public void create() {
        batch = new SpriteBatch();
        gameBoard = GameBoard.getInstance();
        setScreen(gameBoard);
        gameBoard.show();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
        gameBoard.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int width, int height) {
        gameBoard.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        gameBoard.dispose();
    }
}

