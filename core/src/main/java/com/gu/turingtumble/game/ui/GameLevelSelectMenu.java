package com.gu.turingtumble.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.levels.LevelManager;
import com.gu.turingtumble.utils.GameBoard;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;


public class GameLevelSelectMenu implements Screen {
    private MainGame game;
    private GameUIManager uiManager;

    public GameLevelSelectMenu(MainGame game) {
        this.game = game;
        this.uiManager = game.getUiManager();
        Gdx.input.setInputProcessor(uiManager.getUiStage());
        createUI();
    }

    private void createUI() {
        VisTable table = new VisTable();
        table.setFillParent(true);
        uiManager.getUiStage().addActor(table);

        VisWindow window = new VisWindow("Select Level");
        table.add(window).expand().center().top();

        for (int i = 1; i <= 10; i++) {
            VisTextButton levelButton = new VisTextButton("Level " + i);
            levelButton.setDisabled(!LevelManager.isLevelUnlocked(i));
            final int levelNumber = i;
            levelButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    LevelManager.loadLevel(levelNumber);
                    game.setScreen(new GameBoard(game));
                    uiManager.clear();
                }
            });
            window.add(levelButton).pad(10).row();
        }

        VisTextButton backButton = new VisTextButton("Back");
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameMainMenu(game));
            }
        });
        window.add(backButton).pad(10).row();

        window.pack();
        window.setPosition((Gdx.graphics.getWidth() - window.getWidth()) / 2, (Gdx.graphics.getHeight() - window.getHeight()) / 2);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(uiManager.getUiStage());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        uiManager.getUiStage().act(Gdx.graphics.getDeltaTime());
        uiManager.getUiStage().draw();
    }

    @Override
    public void resize(int width, int height) {
        uiManager.getUiStage().getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        uiManager.getUiStage().dispose();
    }
}

