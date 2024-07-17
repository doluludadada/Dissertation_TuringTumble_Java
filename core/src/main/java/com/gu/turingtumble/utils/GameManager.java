package com.gu.turingtumble.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
//import com.gu.turingtumble.gamecomponents.Ball;
import com.gu.turingtumble.gamecomponents.Crossover;
import com.gu.turingtumble.gamecomponents.GameComponents;
import com.gu.turingtumble.gamecomponents.Ramp;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameManager {

    private static GameManager instance;

    private World world;
    private List<GameComponents> gameComponents;
//    private List<Ball> redBalls;
//    private List<Ball> blueBalls;
    private Set<Vector2> slots;
    private Set<Vector2> slotsWithArc;
    private Map<Vector2, GameComponents> components;
    private String selectedComponent;

    private GameManager() {
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void initialise(List<GameComponents> gameComponents, Set<Vector2> slots, Set<Vector2> slotsWithArc, Map<Vector2, GameComponents> components) {
        Box2D.init();
        this.world = new World(new Vector2(0, -9.8f), true);
        this.gameComponents = gameComponents;
//        this.redBalls = redBalls;
//        this.blueBalls = blueBalls;
        this.slots = slots;
        this.slotsWithArc = slotsWithArc;
        this.components = components;
//        initialiseBalls(world, redBalls, blueBalls);
    }

    public World getWorld() {
        return world;
    }

//    private void initialiseBalls(World world, List<Ball> redBalls, List<Ball> blueBalls) {
//        int centreX = GameConstant.SLOT_NUMBER_WIDTH.getValue() / 2 * GameConstant.CELL_SIZE.getValue();
//        int startY = GameConstant.UPPER_SIDE_HEIGHT.getValue() / 3 - 10;
//        int redStartX = centreX + 10 + GameConstant.CELL_SIZE.getValue();
//        int blueStartX = centreX - GameConstant.CELL_SIZE.getValue();
//
//        for (int i = 0; i < GameConstant.RED_BALL_COUNT.getValue(); i++) {
//            redBalls.add(new Ball(world, Color.RED, redStartX, startY + i * 5));
//        }
//        for (int i = 0; i < GameConstant.BLUE_BALL_COUNT.getValue(); i++) {
//            blueBalls.add(new Ball(world, Color.BLUE, blueStartX, startY + i * 5));
//        }
//    }

    public void updateGameLogic(float delta) {
        // 1/60f：每個模擬步驟的時間長度，代表 1/60 秒
        // 6：速度迭代的次數，用於更準確地計算物體的速度
        // 2：位置迭代的次數，用於更準確地計算物體的位置
        world.step(1 / 60f, 6, 2);

    }

    public void handleInput(int x, int y) {
        int col = (int) (x / GameConstant.CELL_SIZE.getValue());
        int row = (int) ((y - GameConstant.UPPER_SIDE_HEIGHT.getValue()) / GameConstant.CELL_SIZE.getValue());

        if (row >= 0 && row < GameConstant.SLOT_NUMBER_HEIGHT.getValue() &&
            col >= 0 && col < GameConstant.SLOT_NUMBER_WIDTH.getValue()) {
            addComponent(col, row);
        }
    }

    private void addComponent(float x, float y) {
        if (selectedComponent != null) {
            GameComponents component = null;
            switch (selectedComponent) {
                case "Ramp":
                    component = new Ramp();
                    gameComponents.add((Ramp) component);
                    break;
                case "Crossover":
                    component = new Crossover();
                    gameComponents.add((Crossover) component);
                    break;
            }
            if (component != null) {
                gameComponents.add(component);
            }
        }
    }
}
