package com.gu.turingtumble.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.levels.LevelManager;
import com.gu.turingtumble.utils.GameBoard;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;


public class GameLevelSelectMenu {
    private MainGame game;
    private Stage stage;


    public GameLevelSelectMenu(MainGame game, Stage stage) {
        this.game = game;
        this.stage = stage;
        createUI();
    }

    private void createUI() {
        VisTable table = new VisTable();
        table.setFillParent(true);
        stage.addActor(table);

        VisWindow window = new VisWindow("Select Level");
        table.add(window).expand().center().top();

        for (int i = 1; i <= 10; i++) {
            VisTextButton levelButton = new VisTextButton("Level " + i);
            levelButton.setDisabled(!LevelManager.isLevelUnlocked(i));
            final int levelNumber = i;
            levelButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    try {
                        LevelManager.loadLevel(levelNumber);
                        game.getUiManager().clear();
                        game.setScreen(new GameBoard(game));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Error loading level: " + e.getMessage());
                    }
                }
            });
            window.add(levelButton).pad(10).row();
        }


        VisTextButton backButton = new VisTextButton("Back");
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getUiManager().showMainMenu();
            }
        });
        window.add(backButton).pad(10).row();

        window.pack();
        window.setPosition((Gdx.graphics.getWidth() - window.getWidth()) / 2, (Gdx.graphics.getHeight() - window.getHeight()) / 2);
    }
}

