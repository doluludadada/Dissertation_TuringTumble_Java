package com.gu.turingtumble.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.gu.turingtumble.components.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gu.turingtumble.utils.GameConstant.*;


public class GameManager {
    private static World world;
    private static List<Ball> redBalls = new ArrayList<>();
    private static List<Ball> blueBalls = new ArrayList<>();
    private static Map<Vector2, Boolean> slotPositions = new HashMap<>(); // true for slots with arc, false for regular slots
    private static Map<Vector2, GameComponents> components = new HashMap<>();
    private static String selectedComponent;
    private static BallStopper redBallStopper;
    private static BallStopper blueBallStopper;
    private static GameBoard gameBoard;


    private static boolean isMirrorSelected = false;


    public static void initialise() {
        if (world == null) {
            initialiseWorld();
        }
        initialiseBallStoppers();
    }


    private static void initialiseWorld() {
        Box2D.init();
        world = new World(new Vector2(0, -9.8f), true);
    }


    public static void initialiseBalls(GameBoard gameBoard) {
        float centreX = (gameBoard.getCameraWidth() + GameConstant.UI_WIDTH.get()) / 2;
        float startY = gameBoard.getCameraHeight() - 30;

        float redStartX = centreX + GameConstant.CELL_SIZE.get();
        float blueStartX = centreX - GameConstant.CELL_SIZE.get();

//        Vector2 ballGravity = new Vector2(0, -19.8f);

        for (int i = 0; i < GameConstant.RED_BALL_COUNT.get(); i++) {
            redBalls.add(new Ball(world, Color.RED, redStartX, startY + i));
        }
        for (int i = 0; i < GameConstant.BLUE_BALL_COUNT.get(); i++) {
            blueBalls.add(new Ball(world, Color.BLUE, blueStartX, startY + i));
        }


    }

    public static void initialiseBallStoppers() {
        float redX = (float) (GameConstant.WINDOW_WIDTH.get() - 2.4 * GameConstant.CELL_SIZE.get());
        float blueX = (float) (GameConstant.UI_WIDTH.get() + 2.4 * GameConstant.CELL_SIZE.get());
        float pos_Y = GameConstant.WINDOW_HEIGHT.get() - 2 * GameConstant.CELL_SIZE.get();

        redBallStopper = new BallStopper(redX, pos_Y, world);
        blueBallStopper = new BallStopper(blueX, pos_Y, world);

    }


    public static void setGravity(Vector2 gravity) {
        if (world != null) {
            world.setGravity(gravity);
        }
    }


    public static void updateGameLogic(float delta) {
        // 1/60f：每個模擬步驟的時間長度，代表 1/60 秒
        // 6：速度迭代的次數，用於更準確地計算物體的速度
        // 2：位置迭代的次數，用於更準確地計算物體的位置
        world.step(1 / 15f, 6, 10);

        for (GameComponents component : components.values()) {
            component.update(delta);
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
                if (canPlaceComponent(slotPosition, isWithArc)) {
                    GameComponents component = ComponentFactory.createComponent(selectedComponent, slotPosition.x, slotPosition.y);
                    components.put(slotPosition, component);
                    component.update(0);
                    break;
                }
            }
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

        // 根據行和列計算位置
        float x = col * CELL_SIZE.get() + offsetX + (CELL_SIZE.get() / 2f) + 140;
        int flippedRow = SLOT_NUMBER_HEIGHT.get() - row - 1;
        float y = flippedRow * CELL_SIZE.get() + offsetY + (CELL_SIZE.get() / 2f) - 30;

        return new Vector2(x, y);

    }


    private static boolean canPlaceComponent(Vector2 slotPosition, boolean isWithArc) {
        if (selectedComponent == null) {
            return false;
        }
        if (isWithArc) {
            return canPlaceInWithArcSlot(selectedComponent);
        } else {
            return canPlaceInAnySlot(selectedComponent);
        }
    }

    private static boolean canPlaceInWithArcSlot(String componentType) {
        return componentType.equals("Ramp") || componentType.equals("Bit") || componentType.equals("Interceptor")
            || componentType.equals("Gear") || componentType.equals("Crossover") || componentType.equals("GearBit");
    }

    private static boolean canPlaceInAnySlot(String componentType) {
        return componentType.equals("Crossover") || componentType.equals("GearBit");
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


    public static boolean isMirrorSelected() {
        return isMirrorSelected;
    }

    public static void setIsMirrorSelected(boolean isMirrorSelected) {
        GameManager.isMirrorSelected = isMirrorSelected;
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


    public static Body createBody(float x, float y, float width, float height, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.0f;

        body.createFixture(fixtureDef);
        shape.dispose();

        return body;
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

    public static BallStopper getRedBallStopper() {
        return redBallStopper;
    }

    public static BallStopper getBlueBallStopper() {
        return blueBallStopper;
    }

    public static void clearComponents() {
        components.clear();
    }

    public static void resetBalls(GameBoard gb) {
        redBalls.clear();
        blueBalls.clear();
        initialiseBalls(gb);
    }

}
