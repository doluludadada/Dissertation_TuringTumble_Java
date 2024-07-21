package com.gu.turingtumble.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.gu.turingtumble.gamecomponents.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameManager {
    private static World world;
    private static List<Ball> redBalls = new ArrayList<>();
    private static List<Ball> blueBalls = new ArrayList<>();
    private static Map<Vector2, GameComponents> components = new HashMap<>();
    private static String selectedComponent;


    private static List<Vector2> slotPositions = new ArrayList<>();
    private static List<Vector2> slotWithArcPositions = new ArrayList<>();



    public static void initialise() {
        initialiseWorld();
        initialiseBalls();
    }


    private static void initialiseWorld() {
        Box2D.init();
        world = new World(new Vector2(0, -9.8f), true);
    }

    public static void initialiseBalls() {
        float centreX = (GameBoard.getInstance().getCameraWidth() + GameConstant.UI_WIDTH.get()) / 2;
        float startY = GameBoard.getInstance().getCameraHeight() - 30;

        float redStartX = centreX + GameConstant.CELL_SIZE.get();
        float blueStartX = centreX - GameConstant.CELL_SIZE.get();


        for (int i = 0; i < GameConstant.RED_BALL_COUNT.get(); i++) {
            redBalls.add(new Ball(world, Color.RED, redStartX, startY + i));
        }
        for (int i = 0; i < GameConstant.BLUE_BALL_COUNT.get(); i++) {
            blueBalls.add(new Ball(world, Color.BLUE, blueStartX, startY + i));
        }

    }

    public static void updateGameLogic(float delta) {
        // 1/60f：每個模擬步驟的時間長度，代表 1/60 秒
        // 6：速度迭代的次數，用於更準確地計算物體的速度
        // 2：位置迭代的次數，用於更準確地計算物體的位置
        world.step(1 / 30f, 6, 2);

    }

    public static void addSlotPosition(Vector2 position, boolean withArc) {
        if (withArc) {
            slotWithArcPositions.add(position);
        } else {
            slotPositions.add(position);
        }
    }

    public static void addComponent(Vector2 position) {

        List<Vector2> SlotPositions = new ArrayList<>(slotWithArcPositions);
        SlotPositions.addAll(slotPositions);


        for (Vector2 slotPosition : SlotPositions) {
            if (position.dst(slotPosition) < GameConstant.CELL_SIZE.get() / 2) {
                if (canPlaceComponent(slotPosition, slotWithArcPositions.contains(slotPosition))) {
                    GameComponents component = ComponentFactory.createComponent(selectedComponent, slotPosition.x, slotPosition.y);
                    components.put(slotPosition, component);
                    break;
                }
            }
        }


    }

    private static boolean canPlaceComponent(Vector2 slotPosition, boolean isWithArc) {
        if (isWithArc) {
            return canPlaceInWithArcSlot(selectedComponent);
        } else {
            return canPlaceInAnySlot(selectedComponent);
        }
    }

    private static boolean canPlaceInWithArcSlot(String componentType) {
        return componentType.equals("Ramp") || componentType.equals("Bit") || componentType.equals("Interceptor") || componentType.equals("Gear");
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

    public static List<Vector2> getSlotPositions() {
        return slotPositions;
    }

    public static List<Vector2> getSlotWithArcPositions() {
        return slotWithArcPositions;
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

}
