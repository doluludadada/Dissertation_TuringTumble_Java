package com.gu.turingtumble.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.gu.turingtumble.gamecomponents.Ball;
import com.gu.turingtumble.gamecomponents.Crossover;
import com.gu.turingtumble.gamecomponents.GameComponents;
import com.gu.turingtumble.gamecomponents.Ramp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameManager {

    private static GameManager instance;

    private World world;
    private List<GameComponents> gameComponents;
    private List<Ball> redBalls;
    private List<Ball> blueBalls;
    private Map<Vector2, GameComponents> components;
    private String selectedComponent;

    private GameManager() {
        gameComponents = new ArrayList<>();
        redBalls = new ArrayList<>();
        blueBalls = new ArrayList<>();
        components = new HashMap<>();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void initialise() {
        Box2D.init();
        this.world = new World(new Vector2(0, -9.8f), true);
        initialiseBalls();
    }


    public World getWorld() {
        return world;
    }

    public void initialiseBalls() {


        float centreX = GameBoard.getInstance().getCameraWidth() / 2;
        float startY = GameBoard.getInstance().getCameraHeight();
        float redStartX = centreX + 10 + GameConstant.CELL_SIZE.getValue();
        float blueStartX = centreX - GameConstant.CELL_SIZE.getValue();


        for (int i = 0; i < GameConstant.RED_BALL_COUNT.getValue(); i++) {
            redBalls.add(new Ball(world, Color.RED, redStartX, startY + i));
        }
        for (int i = 0; i < GameConstant.BLUE_BALL_COUNT.getValue(); i++) {
            blueBalls.add(new Ball(world, Color.BLUE, blueStartX, startY + i));
        }

    }

    public void updateGameLogic(float delta) {
        // 1/60f：每個模擬步驟的時間長度，代表 1/60 秒
        // 6：速度迭代的次數，用於更準確地計算物體的速度
        // 2：位置迭代的次數，用於更準確地計算物體的位置
        world.step(1 / 60f, 6, 2);

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

    public List<Ball> getBlueBalls() {
        return blueBalls;
    }

    public List<Ball> getRedBalls() {
        return redBalls;
    }

}
