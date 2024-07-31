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

/**
 * GameUI class for creating in game user interface.
 */

public class GameUI {
    private final MainGame game;
    private final Stage stage;

    /**
     * Constructor for GameUI. Initializes the stage and creates the UI.
     *
     * @param stage The stage where the UI elements will be added.
     */
    public GameUI(MainGame game, Stage stage) {
        this.game = game;
        this.stage = stage;
        createUI();
    }

    /**
     * Creates the UI layout and elements.
     */
    private void createUI() {
        VisTable table = new VisTable();
        table.setFillParent(true);
        stage.addActor(table);

        VisWindow window = new VisWindow("Game UI");
        table.add(window).expand().top().left();

        //component selection buttons
        addComponentButton(window, "Ramp");
        addComponentButton(window, "Crossover");
        addComponentButton(window, "Bit");
        addComponentButton(window, "Interceptor");
        addComponentButton(window, "Gear");
        addComponentButton(window, "GearBit");
        //functional buttons
        addResetButton(window);
        addBackButton(window);

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
        VisTable buttonTable = new VisTable();

        VisTextButton button = new VisTextButton("Add " + componentType);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameManager.setSelectedComponent(componentType);
                GameManager.setIsMirrorSelected(false);
            }
        });

        buttonTable.add(button).pad(10);

        window.add(buttonTable).row();
    }


    /**
     * Adds a back button to the window.
     *
     * @param window The window to which the button will be added.
     */
    private void addBackButton(VisWindow window) {
        VisTextButton backButton = new VisTextButton("Back");
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getUiManager().showMainMenu();
            }
        });
        window.add(backButton).pad(10).row();
    }


    /**
     * Adds a reset button to the window.
     *
     * @param window The window to which the button will be added.
     */
    private void addResetButton(VisWindow window) {
        VisTextButton resetButton = new VisTextButton("Reset");
        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameManager.resetLevel();
            }
        });
        window.add(resetButton).pad(10).row();
    }


}
