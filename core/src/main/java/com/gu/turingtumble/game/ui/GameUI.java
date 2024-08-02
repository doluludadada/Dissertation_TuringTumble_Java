package com.gu.turingtumble.game.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.levels.Level;
import com.gu.turingtumble.levels.LevelManager;
import com.gu.turingtumble.utils.GameManager;
import com.gu.turingtumble.utils.GameState;
import com.kotcrab.vis.ui.widget.*;

import java.util.*;

/**
 * GameUI class for creating in game user interface.
 */

public class GameUI {
    private final MainGame game;
    private final Stage uiStage;
    private final Map<String, VisLabel> componentLabels = new HashMap<>();


    /**
     * Constructor for GameUI. Initializes the stage and creates the UI.
     *
     * @param uiStage The stage where the UI elements will be added.
     */
    public GameUI(MainGame game, Stage uiStage) {
        this.game = game;
        this.uiStage = uiStage;
        createUI();
        // new Table for Victory condition.
        victoryUI();
    }


    /**
     * Creates the UI layout and elements.
     */
    private void createUI() {
        VisTable table = new VisTable();
        table.setFillParent(true);
        uiStage.addActor(table);

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


    private void victoryUI() {
        VisWindow victoryWindow = new VisWindow("Challenge requirements:");
        updateVictoryConditionPanel(victoryWindow);
        uiStage.addActor(victoryWindow);
        victoryWindow.setPosition(10, 10);
        victoryWindow.pack();
        victoryWindow.setMovable(true);
    }

    /**
     * Adds a component button to the window.
     *
     * @param window        The window to which the button will be added.
     * @param componentType The type of component the button will add.
     */
    private void addComponentButton(VisWindow window, final String componentType) {
        VisTable buttonTable = new VisTable();

        VisTextButton button = new VisTextButton("" + componentType);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameManager.setSelectedComponent(componentType);
            }
        });

        Level currentLevel = LevelManager.getCurrentLevel();
        int remainingCount = 0;
        if (currentLevel != null) {
            remainingCount = currentLevel.getLeftComponentCount(componentType);
            if (remainingCount <= 0 || !currentLevel.componentLimit(componentType)) {
                button.setDisabled(true);
                button.setColor(Color.DARK_GRAY);
            }
        }

        VisLabel countLabel = new VisLabel("Left: " + remainingCount);
        componentLabels.put(componentType, countLabel);

        buttonTable.add(button).pad(10);
        buttonTable.add(countLabel).pad(10);
        window.add(buttonTable).row();
    }


    /**
     * Adds a back button to the window.
     *
     * @param window The window to which the button will be added.
     */
    private void addBackButton(VisWindow window) {
        VisTextButton backButton = new VisTextButton("Back to Lobby");
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.restartGame();
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
                updateUI();
            }
        });
        window.add(resetButton).pad(10).row();
    }


    private void updateVictoryConditionPanel(VisWindow victoryWindow) {
        victoryWindow.clear();

        GameState gameState = GameManager.getGameState();
        int goalBlueBall = gameState.getGoalBlueBall();
        int goalRedBall = gameState.getGoalRedBall();

        // Create tables for blue and red balls
        VisTable blueBallTable = createBallTable("Blue Balls:", goalBlueBall, Color.BLUE);
        VisTable redBallTable = createBallTable("Red Balls:", goalRedBall, Color.RED);

        // Add the tables to the victory window
        victoryWindow.add(blueBallTable).left().pad(2).row();
        victoryWindow.add(redBallTable).left().pad(2).row();

        victoryWindow.pack();
    }

    private VisTable createBallTable(String label, int goalBall, Color color) {
        VisTable table = new VisTable();
        table.left().top();

        // Add label
        VisLabel ballLabel = new VisLabel(label);
        table.add(ballLabel).left().pad(2).row();

        // Create a new table for the balls
        VisTable ballsTable = new VisTable();
        ballsTable.left().top();

        // Add balls to the balls table
        for (int i = 0; i < 8; i++) {
            VisImage ballImage = createBallImage(i < goalBall, color);
            ballsTable.add(ballImage).size(20).pad(2);
        }

        // Add the balls table to the main table
        table.add(ballsTable).left().pad(2).row();

        return table;
    }

    private VisImage createBallImage(boolean isActive, Color color) {
        String texturePath;
        if (isActive) {
            if (color == Color.BLUE) {
                texturePath = "blue_ball.png";
            } else {
                texturePath = "red_ball.png";
            }
        } else {
            texturePath = "gray_ball.png";
        }
        Texture ballTexture = new Texture(Gdx.files.internal(texturePath));
        return new VisImage(ballTexture);
    }



    public void updateUI() {
        Level currentLevel = LevelManager.getCurrentLevel();
        if (currentLevel != null) {
            for (Map.Entry<String, VisLabel> entry : componentLabels.entrySet()) {
                String componentType = entry.getKey();
                VisLabel label = entry.getValue();
                int remainingCount = currentLevel.getLeftComponentCount(componentType);
                label.setText("Left: " + remainingCount);
            }
        }


        for (Actor actor : uiStage.getActors()) {
            if (actor instanceof VisWindow && ((VisWindow) actor).getTitleLabel().getText().toString().equals("Challenge requirements:")) {
                updateVictoryConditionPanel((VisWindow) actor);
                break;
            }
        }

    }

}
