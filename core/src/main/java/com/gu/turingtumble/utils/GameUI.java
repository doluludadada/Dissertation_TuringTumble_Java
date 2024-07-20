package com.gu.turingtumble.utils;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;

public class GameUI {
    private Stage stage;

    public GameUI(Stage stage) {
        this.stage = stage;
        createUI();
    }


    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        VisWindow window = new VisWindow ("Game UI");
        table.add(window).expand().top().left();

        VisTextButton addButton = new VisTextButton("Add Component");
        window.add(addButton).pad(10);

        window.row();

        VisTextButton removeButton = new VisTextButton("Remove Component");
        window.add(removeButton).pad(10);

        window.pack();
        window.setPosition(10, Gdx.graphics.getHeight() - window.getHeight());
    }

    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void resize(int width, int height) {

        stage.getViewport().update(GameConstant.UI_WIDTH.get(), height, true);

    }

    public void dispose() {
        stage.dispose();
        VisUI.dispose();
    }
}



