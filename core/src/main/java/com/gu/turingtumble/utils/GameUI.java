package com.gu.turingtumble.utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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

        VisWindow window = new VisWindow("Game UI");
        table.add(window).expand().top().left();

        // Adding component selection buttons
        addButton(window, "Ramp");
        addButton(window, "Crossover");
        addButton(window, "Bit");
        addButton(window, "Interceptor");
        addButton(window, "Gear");
        addButton(window, "GearBit");

        window.pack();
        window.setPosition(10, Gdx.graphics.getHeight() - window.getHeight());
    }

    private void addButton(VisWindow window, final String componentType) {
        VisTextButton button = new VisTextButton("Add " + componentType);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameManager.setSelectedComponent(componentType);
            }
        });
        window.add(button).pad(10);
        window.row();
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



