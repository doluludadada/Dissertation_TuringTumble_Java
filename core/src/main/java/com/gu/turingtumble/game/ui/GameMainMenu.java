package com.gu.turingtumble.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.utils.GameManager;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;

public class GameMainMenu {
    private final MainGame game;
    private final Stage stage;


    public GameMainMenu(MainGame game, Stage stage) {
        this.game = game;
        this.stage = stage;
        createUI();
    }

    private void createUI() {
        VisTable table = new VisTable();
        table.setFillParent(true);
        stage.addActor(table);

        VisWindow window = new VisWindow("Main Menu");
        table.add(window).expand().center();



        VisTextButton newGameButton = new VisTextButton("New Game");
        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameManager.startGame(game, 1);
            }
        });

        window.add(newGameButton).pad(10).row();



        VisTextButton levelSelectButton = new VisTextButton("Level Select");
        levelSelectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MainGame.getUiManager().showLevelSelectMenu();
            }
        });
        window.add(levelSelectButton).pad(10).row();

        VisTextButton exitButton = new VisTextButton("Exit");
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        window.add(exitButton).pad(10).row();

        window.pack();
        window.setPosition((Gdx.graphics.getWidth() - window.getWidth()) / 2, (Gdx.graphics.getHeight() - window.getHeight()) / 2);
    }

}
