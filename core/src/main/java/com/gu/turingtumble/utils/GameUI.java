package com.gu.turingtumble.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;


public class GameUI {
    private Stage stage;
    private Skin skin;

    public GameUI() {
        VisUI.load();
        skin = VisUI.getSkin();
        stage = new Stage(new ScreenViewport());


        Gdx.input.setInputProcessor(stage);


    }

    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // 創建窗口
        Window window = new Window("Game UI", skin);
        table.add(window).expand().top().right();

        // 添加按鈕
        TextButton addButton = new TextButton("Add Component", skin);
        window.add(addButton).pad(10);

        window.row();

        TextButton removeButton = new TextButton("Remove Component", skin);
        window.add(removeButton).pad(10);

        // 設置窗口的大小和位置
        window.pack();
        window.setPosition(Gdx.graphics.getWidth() - window.getWidth(), Gdx.graphics.getHeight() - window.getHeight());
    }

    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        VisUI.dispose();
    }

}
