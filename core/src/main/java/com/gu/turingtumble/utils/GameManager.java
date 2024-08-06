package com.gu.turingtumble.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.components.*;
import com.gu.turingtumble.levels.LevelManager;

import java.io.*;
import java.util.*;


import static com.gu.turingtumble.utils.GameConstant.*;


public class GameManager {
    private static World world;
    private static List<Ball> redBalls = new ArrayList<>();
    private static List<Ball> blueBalls = new ArrayList<>();
    private static Map<Vector2, Boolean> slotPositions = new HashMap<>();                                 // true for slots with arc, false for regular slots
    private static Map<Vector2, Body> slotBodies = new HashMap<>();
    private static Map<Vector2, GameComponents> components = new HashMap<>();
    private static String selectedComponent;
    private static BallStopper redBallStopper;
    private static BallStopper blueBallStopper;
    private static BottomSensor bottomSensor;
    private static GameBoard gameBoard;
    private static GameState gameState;

    //    SAVE MESSAGE
    private static final String SAVE_FILE = "player_save.dat";
    private static Map<String, Integer> saves = new HashMap<>();


    public static void startGame(MainGame game, int levelNum) {
        clearAll();
        initialise(game);
        game.setScreen(gameBoard);
        LevelManager.loadLevel(levelNum);
        MainGame.getUiManager().updateUI();
    }


    public static void initialise(MainGame game) {
        initialiseWorld();
        initialiseBoard(game);
        createAllSlotBody(world);
        initialiseBallStoppers();
        initialiseBalls();
        gameState = new GameState();
    }


    private static void initialiseWorld() {
        if (world == null) {
            Box2D.init();
            world = new World(new Vector2(0, -10f), true);
        }
    }

    private static void initialiseBoard(MainGame game) {
        if (gameBoard == null) {
            gameBoard = new GameBoard(game);
        }
    }


    public static void initialiseBalls() {
//      reset balls
        redBalls.clear();
        blueBalls.clear();

        //      coordinate
//        float centreX = (gameBoard.getCameraWidth() + GameConstant.UI_WIDTH.get()) / 2;
//        float startY = gameBoard.getCameraHeight() - 30;
        float centreX = UI_WIDTH.get() + (GAME_WIDTH.get() / 2f);
        float startY = WINDOW_HEIGHT.get() - 30;

        float redStartX = centreX + GameConstant.CELL_SIZE.get();
        float blueStartX = centreX - GameConstant.CELL_SIZE.get();

        //      Set the balls
        for (int i = 0; i < GameConstant.RED_BALL_COUNT.get(); i++) {
            redBalls.add(new Ball(world, Color.RED, redStartX, startY + i));
        }
        for (int i = 0; i < GameConstant.BLUE_BALL_COUNT.get(); i++) {
            blueBalls.add(new Ball(world, Color.BLUE, blueStartX, startY + i));
        }
    }

    private static void initialiseBallStoppers() {
        //      coordinate
        float redX = (GameConstant.WINDOW_WIDTH.get() - 0.5f * GameConstant.CELL_SIZE.get());
        float blueX = (GameConstant.UI_WIDTH.get() + 0.5f * GameConstant.CELL_SIZE.get());
        float pos_Y = GameConstant.WINDOW_HEIGHT.get() - 1.4f * GameConstant.CELL_SIZE.get();

        //      sensor X,Y
        float sensorPosX = UI_WIDTH.get() + (GAME_WIDTH.get() / 2f);
        float sensorPosY = 50;

        redBallStopper = new BallStopper(redX, pos_Y, world);
        blueBallStopper = new BallStopper(blueX, pos_Y, world);
        bottomSensor = new BottomSensor(sensorPosX, sensorPosY, world);
    }

    public static void updateGameLogic(float delta) {

        updateStopperLogic();

/**
 *         1/60f：每個模擬步驟的時間長度，代表 1/60 秒
 *         6：速度迭代的次數，用於更準確地計算物體的速度
 *         2：位置迭代的次數，用於更準確地計算物體的位置
 */
        world.step(1 / 15f, 6, 10);

        for (GameComponents component : components.values()) {
            component.update(delta);
        }

        if (LevelManager.getCurrentLevel().isComplete()) {
            LevelManager.unlockLevel(LevelManager.getCurrentLevelNumber() + 1);
        }

        LevelManager.checkLevelCompletion();

    }


    private static void updateStopperLogic() {
        if (redBallStopper != null) {
            redBallStopper.update();
        }
        if (blueBallStopper != null) {
            blueBallStopper.update();
        }
    }


    public static void addSlotPosition(Vector2 position, boolean withArc) {
        slotPositions.put(position, withArc);
    }


    public static void addComponent(Vector2 position) {
        for (Map.Entry<Vector2, Boolean> entry : slotPositions.entrySet()) {
            Vector2 slotPosition = entry.getKey();
            boolean isWithArc = entry.getValue();

            if (position.dst(slotPosition) < CELL_SIZE.get() / 2) {
                if (components.containsKey(slotPosition)) {
                    return;
                }
                if (canPlaceComponent(selectedComponent, isWithArc)) {
                    GameComponents component = ComponentFactory.createComponent(selectedComponent, slotPosition.x, slotPosition.y);
                    components.put(slotPosition, component);
                    component.update(0);
                    adjustComponentLimit(selectedComponent);
                    break;
                }
            }
        }
    }

    private static void adjustComponentLimit(String componentType) {
        if (LevelManager.getCurrentLevel() != null) {
            LevelManager.getCurrentLevel().plusComponentCount(componentType);
        }
    }


    /**
     * @param row
     * @param col
     */
    public static void addComponent(int row, int col) {
        Vector2 position = getSlotRowColPosition(row, col);     ///some problems
        if (components.containsKey(position)) {
            return;
        }
        GameComponents component = ComponentFactory.createComponent(selectedComponent, position.x, position.y);
        components.put(position, component);
        component.update(0);
    }


    public static Vector2 getSlotRowColPosition(int row, int col) {

        float width = UI_WIDTH.get() + GAME_WIDTH.get();
        float height = WINDOW_HEIGHT.get();
        float offsetX = (width - SLOT_NUMBER_WIDTH.get() * CELL_SIZE.get()) / 2;
        float offsetY = (height - SLOT_NUMBER_HEIGHT.get() * CELL_SIZE.get()) / 2;


        float x = col * CELL_SIZE.get() + offsetX + (CELL_SIZE.get() / 2f) + 140;
        int flippedRow = SLOT_NUMBER_HEIGHT.get() - row - 1;
        float y = flippedRow * CELL_SIZE.get() + offsetY + (CELL_SIZE.get() / 2f) - 30;

        return new Vector2(x, y);

    }


    private static boolean canPlaceComponent(String componentType, boolean isWithArc) {
        if (selectedComponent == null) {
            return false;
        }

        if (!LevelManager.getCurrentLevel().componentLimit(componentType)) {
            return false;
        }

        if (isWithArc) {
            return canPlaceInWithArcSlot(selectedComponent);
        } else {
            return canPlaceInAnySlot(selectedComponent);
        }
    }

    private static boolean canPlaceInWithArcSlot(String componentType) {
        return componentType.equals("Ramp") || componentType.equals("Bit") || componentType.equals("Interceptor") || componentType.equals("Gear") || componentType.equals("Crossover") || componentType.equals("GearBit");
    }

    private static boolean canPlaceInAnySlot(String componentType) {
        return componentType.equals("Crossover") || componentType.equals("GearBit");
    }


    public static boolean isSlotWithArc(int row, int col) {
        if (row == 0) {
            return (col == 3 || col == 7);
        }
        if (row == 1) {
            return (col > 1 && col < 9 && col % 2 == 0);
        }
        if (row >= 2 && row <= 9) {
            if ((row % 2 == 0 && col % 2 == 1) || (row % 2 == 1 && col % 2 == 0)) {
                return true;
            }
        }
        if (row == 10 && col == 5) {
            return true;
        }
        return false;
    }

    public static boolean isSlot(int row, int col) {
        if (row == 0) {
            return (col == 2 || col == 4 || col == 6 || col == 8);
        }
        if (row == 1) {
            return (col <= 9 && col % 2 == 1);
        }
        if (row >= 2 && row <= 9) {
            if ((row % 2 == 0 && col % 2 == 0) || (row % 2 == 1 && col % 2 == 1)) {
                return true;
            }
        }
        return false;
    }

    public static Map<Vector2, GameComponents> getComponents() {
        return components;
    }

    public static Body createBody(World world, BodyDef.BodyType bodyType, float pos_x, float pos_y, String name) {
        // 1. Create a BodyDef
        BodyDef bd = new BodyDef();
        bd.type = bodyType;
        bd.position.set(pos_x, pos_y);

        // 2. Create a Body
        Body body = world.createBody(bd);
        body.setUserData(name);

        return body;
    }

    public static void clearComponents() {
        for (GameComponents component : components.values()) {

            Body body = component.getBody();
            if (body != null) {
                world.destroyBody(body);
            }


//            Body slotBody = ComponentFactory.getSlotBodyForComponent(component);
//            if (slotBody != null) {
//                world.destroyBody(slotBody);
//            }

//            ComponentFactory.removeSlotBodyForComponent(component);
        }
        components.clear();
    }


    public static void clearBallStoppersAndSensor() {
        if (redBallStopper != null) {
            Body body = redBallStopper.getBody();
            if (body != null) {
                world.destroyBody(body);
            }
            redBallStopper = null;
        }
        if (blueBallStopper != null) {
            Body body = blueBallStopper.getBody();
            if (body != null) {
                world.destroyBody(body);
            }
            blueBallStopper = null;
        }
        if (bottomSensor != null) {
            Body body = bottomSensor.getBody();
            if (body != null) {
                world.destroyBody(body);
            }
            bottomSensor = null;
        }
    }


    public static boolean toggleComponentMirror(Vector2 position) {
        for (Map.Entry<Vector2, GameComponents> entry : components.entrySet()) {
            Vector2 slotPosition = entry.getKey();
            GameComponents component = entry.getValue();

            if (position.dst(slotPosition) < GameConstant.CELL_SIZE.get() / 2) {
                if (component instanceof MirrorRamp) {
                    replaceComponent(slotPosition, "Ramp", component);
                    return true;
                }

                if (component instanceof Ramp) {
                    replaceComponent(slotPosition, "MirrorRamp", component);
                    return true;
                }
                break;
            }
        }
        return false;
    }


    private static void replaceComponent(Vector2 slotPosition, String newComponentType, GameComponents oldComponent) {
//        Body slotBody = ComponentFactory.getSlotBodyForComponent(oldComponent);
//        if (slotBody != null) {
//            GameManager.getWorld().destroyBody(slotBody);
//            ComponentFactory.removeSlotBodyForComponent(oldComponent);
//        }

        if (oldComponent.getBody() != null) {
            GameManager.getWorld().destroyBody(oldComponent.getBody());
        }


        GameComponents newComponent = ComponentFactory.createComponent(
            newComponentType,
            slotPosition.x,
            slotPosition.y
        );
        components.put(slotPosition, newComponent);
    }

    public static void createAllSlotBody(World world) {
        for (Vector2 slotPos : slotPositions.keySet()) {
            slotBodies.put(slotPos, createSlotBody(slotPos.x, slotPos.y, world));
        }
    }

    private static Body createSlotBody(float x, float y, World world) {
        BodyDef slotDef = new BodyDef();
        slotDef.type = BodyDef.BodyType.KinematicBody;
        slotDef.position.set(x, y);
        Body slotBody = world.createBody(slotDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;
        slotBody.createFixture(fixtureDef);
        shape.dispose();

        return slotBody;
    }

    public static Body getSlotBody(Vector2 position) {
        return slotBodies.get(position);
    }

    public static void clearBalls() {
        for (Ball ball : redBalls) {
            Body body = ball.getBallBody();
            if (body != null) {
                world.destroyBody(body);
            }
        }
        for (Ball ball : blueBalls) {
            Body body = ball.getBallBody();
            if (body != null) {
                world.destroyBody(body);
            }
        }
        redBalls.clear();
        blueBalls.clear();
    }


    public static void resetLevel() {
        clearComponents();
        clearBalls();
        clearBallStoppersAndSensor();

        LevelManager.getCurrentLevel().initialise();
        initialiseBallStoppers();
        initialiseBalls();
    }

    public static void clearAll() {
        clearComponents();
        clearBalls();
        clearBallStoppersAndSensor();
        if (world != null) {
            world.dispose();
            world = null;
        }
        redBalls.clear();
        blueBalls.clear();
        slotPositions.clear();
        components.clear();
        selectedComponent = null;
        redBallStopper = null;
        blueBallStopper = null;
        bottomSensor = null;

        if (gameBoard != null) {
            gameBoard.dispose();
            gameBoard = null;
        }
    }


    /**
     * Loads player records from file.
     */
    public static void loadRecords() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            saves = (Map<String, Integer>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Record file not found, creating new one.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves player records to file.
     */
    public static void saveRecords() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(saves);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the record for a specific level.
     *
     * @param level The level number
     * @param score The score to save
     */
    public static void updateRecord(int level, int score) {
        saves.put("Level " + level, score);
        saveRecords();
    }

    /**
     * Gets the record for a specific level.
     *
     * @param level The level number
     * @return The score of the specified level
     */
    public static int getRecord(int level) {
        return saves.getOrDefault("Level " + level, 0);
    }


    /**
     *
     */

    public static BallStopper getRedBallStopper() {
        return redBallStopper;
    }

    public static BallStopper getBlueBallStopper() {
        return blueBallStopper;
    }

    public static void giveBallEnergy() {
        List<Ball> balls = new ArrayList<>();
        balls.addAll(redBalls);
        balls.addAll(blueBalls);

        for (Ball ball : balls) {
            ball.getBallBody().applyLinearImpulse(new Vector2(0f, 20f), ball.getBallBody().getPosition(), true);
        }
    }

    public static void setSelectedComponent(String componentType) {
        selectedComponent = componentType;
    }

    public static List<Ball> getBlueBalls() {
        return blueBalls;
    }

    public static List<Ball> getRedBalls() {
        return redBalls;
    }

    public static World getWorld() {
        return world;
    }

    public static GameState getGameState() {
        return gameState;
    }

    public static GameBoard getGameBoard() {
        return gameBoard;
    }

    public static BottomSensor getBottomSensor() {
        return bottomSensor;
    }

    public static Map<Vector2, Boolean> getSlotPositions() {
        return slotPositions;
    }

}
