package com.gu.turingtumble.game.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gu.turingtumble.utils.GameConstant;
import com.gu.turingtumble.utils.GameManager;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;

/**
 * GameUI class for creating in game user interface.
 */

public class GameUI {

    private Stage stage;


    /**
     * Constructor for GameUI. Initializes the stage and creates the UI.
     *
     * @param stage The stage where the UI elements will be added.
     */
    public GameUI(Stage stage) {
        this.stage = stage;
        this.stage.clear();
        createUI();
    }

    /**
     * Creates the UI layout and elements.
     */
    private void createUI() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        VisWindow window = new VisWindow("Game UI");
        table.add(window).expand().top().left();

        // Adding component selection buttons
        addComponentButton(window, "Ramp");
        addComponentButton(window, "Crossover");
        addComponentButton(window, "Bit");
        addComponentButton(window, "Interceptor");
        addComponentButton(window, "Gear");
        addComponentButton(window, "GearBit");

        window.pack();
        window.setPosition(10, Gdx.graphics.getHeight() - window.getHeight());
    }



    /**
     * Adds a component button to the window.
     *
     * @param window        The window to which the button will be added.
     * @param componentType The type of component the button will add.
     */
    private void addComponentButton(VisWindow window, final String componentType) {
        Table buttonTable = new Table();

        VisTextButton button = new VisTextButton("Add " + componentType);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameManager.setSelectedComponent(componentType);
                GameManager.setIsMirrorSelected(false);
            }
        });

        VisTextButton mirrorButton = new VisTextButton("M");
        mirrorButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameManager.setSelectedComponent(componentType);
                GameManager.setIsMirrorSelected(true);
            }
        });

        buttonTable.add(button).pad(10);
        buttonTable.add(mirrorButton).pad(10);

        window.add(buttonTable).row();
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
    }
}
