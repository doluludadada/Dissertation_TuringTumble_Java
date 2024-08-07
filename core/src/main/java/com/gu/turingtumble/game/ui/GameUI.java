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

    private boolean isErrorDialogShown = false;


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
        addSpeedButton(window);
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
        VisTextButton backButton = new VisTextButton("Exit the game");
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.restartApplication();
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
                GameState.resetCurrentState(GameManager.getGameState().getRequireGoal().size());
                updateUI();
            }
        });
        window.add(resetButton).pad(10).row();
    }

    private void addSpeedButton(VisWindow window) {
        VisTextButton speedButton = new VisTextButton("5X Speed");
        speedButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameManager.toggleGameSpeed();
            }
        });
        window.add(speedButton).pad(10).row();
    }


    private void updateVictoryConditionPanel(VisWindow victoryWindow) {
        victoryWindow.clear();

        GameState gameState = GameManager.getGameState();
        List<Integer> requireGoal = gameState.getRequireGoal();
        List<Integer> currentState = gameState.getCurrentState();

        // Create tables for required and current outputs
        VisTable requiredOutputTable = createOutputTable("Output Requirement:", requireGoal);
        VisTable currentOutputTable = createOutputTable("Current Output:", currentState);

        // Add the tables to the victory window
        victoryWindow.add(requiredOutputTable).left().pad(2).row();
        victoryWindow.add(currentOutputTable).left().pad(2).row();

        victoryWindow.pack();
    }

    // This method creates a table to display the output balls
    private VisTable createOutputTable(String label, List<Integer> output) {
        VisTable table = new VisTable();
        table.left().top();

        // Add label
        VisLabel outputLabel = new VisLabel(label);
        table.add(outputLabel).left().pad(2).row();

        // Create a new table for the output balls
        VisTable ballsTable = new VisTable();
        ballsTable.left().top();

        // Add balls to the balls table
        for (int value : output) {
            Color color = value == 0 ? Color.BLUE : (value == 1 ? Color.RED : Color.GRAY);  // Gray for default
            VisImage ballImage = createBallImage(true, color);
            ballsTable.add(ballImage).size(20).pad(2);
        }

        // Add the balls table to the main table
        table.add(ballsTable).left().pad(2).row();

        return table;
    }

    // This method creates a ball image with the specified color
    private VisImage createBallImage(boolean isActive, Color color) {
        String texturePath;
        if (isActive) {
            if (color == Color.BLUE) {
                texturePath = "blue_ball.png";
            } else if (color == Color.RED) {
                texturePath = "red_ball.png";
            } else {
                texturePath = "gray_ball.png";
            }
        } else {
            texturePath = "gray_ball.png";
        }
        Texture ballTexture = new Texture(Gdx.files.internal(texturePath));
        return new VisImage(ballTexture);
    }

    // This method updates the UI components
    public void updateUI() {
        Level currentLevel = LevelManager.getCurrentLevel();
        if (currentLevel != null) {
            for (Map.Entry<String, VisLabel> entry : componentLabels.entrySet()) {
                String componentType = entry.getKey();
                VisLabel label = entry.getValue();
                int remainingCount = currentLevel.getLeftComponentCount(componentType);
                label.setText("Left: " + remainingCount);

                VisTextButton button = (VisTextButton) label.getParent().getChildren().get(0);
                if (remainingCount <= 0) {
                    button.setDisabled(true);
                    button.setColor(Color.DARK_GRAY);
                } else {
                    button.setDisabled(false);
                    button.setColor(Color.WHITE);
                }
            }
        }

        for (Actor actor : uiStage.getActors()) {
            if (actor instanceof VisWindow && ((VisWindow) actor).getTitleLabel().getText().toString().equals("Challenge requirements:")) {
                updateVictoryConditionPanel((VisWindow) actor);
                break;
            }
        }

        if (GameState.checkOutput()) {
            showErrorOutputDialog();
        }

    }


    public void showLevelCompleteDialog() {
        VisDialog dialog = new VisDialog("Level Complete") {
            @Override
            protected void result(Object object) {
                GameManager.startGame(game, LevelManager.getCurrentLevelNumber() + 1);
                updateUI();
            }
        };
        dialog.text("You have completed the level!!");
        dialog.button("OK", true);
        dialog.show(uiStage);
    }

    private void showErrorOutputDialog() {
        if (isErrorDialogShown) {
            return; // Prevent multiple dialogs
        }
        isErrorDialogShown = true;

        GameManager.pauseGame(); // Pause the game

        VisDialog dialog = new VisDialog("Configuration Error") {
            @Override
            protected void result(Object object) {
                if (object.equals("resetBalls")) {
                    GameState.resetCurrentState(GameManager.getGameState().getRequireGoal().size());
                    MainGame.getUiManager().updateUI();
                } else if (object.equals("resetLevel")) {
                    GameManager.resetLevel();
                    GameState.resetCurrentState(GameManager.getGameState().getRequireGoal().size());
                    MainGame.getUiManager().updateUI();
                }
                GameManager.resumeGame(); // Resume the game
                isErrorDialogShown = false;
            }

            @Override
            public void hide() {
                super.hide();
                GameManager.resumeGame(); // Resume the game if dialog is closed without selecting an option
                isErrorDialogShown = false;
            }
        };
        dialog.text("The current ball output does not match the required configuration.\nWould you like to try again?");
        dialog.button("Reset Balls", "resetBalls");
        dialog.button("Reset Level", "resetLevel");
        dialog.show(uiStage);
    }



}

