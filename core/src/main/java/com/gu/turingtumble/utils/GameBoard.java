package com.gu.turingtumble.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
//import com.gu.turingtumble.gamecomponents.Ball;
import com.gu.turingtumble.gamecomponents.GameComponents;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.*;

public class GameBoard implements Screen {

    private static GameBoard gameBoard = null;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private Box2DDebugRenderer debugRenderer;
    private List<GameComponents> gameComponents;
//    private List<Ball> redBalls;
//    private List<Ball> blueBalls;



    private Set<Vector2> slots;
    private Set<Vector2> slotsWithArc;
    private Map<Vector2, GameComponents> components;

    private GameManager gameManager;


    private final float WORLD_WIDTH = GameConstant.WINDOW_WIDTH.getValue();
    private final float WORLD_HEIGHT = GameConstant.WINDOW_HEIGHT.getValue();
    private final int SLOT_NUMBER_WIDTH = GameConstant.SLOT_NUMBER_WIDTH.getValue();
    private final int SLOT_NUMBER_HEIGHT = GameConstant.SLOT_NUMBER_HEIGHT.getValue();
    private final int CELL_SIZE = GameConstant.CELL_SIZE.getValue();


    private GameBoard() {
    }

    public static GameBoard getInstance() {
        if (GameBoard.gameBoard == null) {
            GameBoard.gameBoard = new GameBoard();
        }
        return GameBoard.gameBoard;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 900);
        batch = new SpriteBatch();

//        redBalls = new ArrayList<>();
//        blueBalls = new ArrayList<>();
        gameComponents = new ArrayList<>();

        slots = new HashSet<>();
        slotsWithArc = new HashSet<>();
        components = new HashMap<>();

        gameManager = GameManager.getInstance();
        gameManager.initialise(gameComponents, slots, slotsWithArc, components);

        debugRenderer = new Box2DDebugRenderer();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();


//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.end();


    }


    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        gameManager.getWorld().dispose();
        debugRenderer.dispose();
        for (GameComponents component : gameComponents) {
            if (component instanceof Disposable) {
                ((Disposable) component).dispose();
            }
        }
//        for (Ball ball : redBalls) {
//            ball.dispose();
//        }
    }


    private void update(float delta) {
        gameManager.updateGameLogic(delta);
        camera.update();


//        deal mouse event
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            gameManager.handleInput((int) touchPos.x, (int) touchPos.y);
        }
    }


    private void draw() {
        Gdx.gl.glClearColor(1, 1, 1, 1);       //white
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.setProjectionMatrix(camera.combined);

        drawBoard();


        debugRenderer.render(gameManager.getWorld(), camera.combined);

    }

    private void drawBoard() {
        float width = camera.viewportWidth;
        float height = camera.viewportHeight;


        // Draw background
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0, 0, width, height, Color.WHITE, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY);
        shapeRenderer.end();

        // Draw border
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        Gdx.gl.glLineWidth(50);
        shapeRenderer.rect(0, 0, width, height);

        drawBoardSlot();

        shapeRenderer.end();
    }

    private void drawBoardSlot() {
        for (int row = 0; row < SLOT_NUMBER_HEIGHT; row++) {
            for (int col = 0; col < SLOT_NUMBER_WIDTH; col++) {
                int x = col * CELL_SIZE + CELL_SIZE / 2;

                // Flipping the row index vertically
                int flippedRow = SLOT_NUMBER_HEIGHT - row - 1;
                // Calculating the y-coordinate for the slot
                int baseY = flippedRow * CELL_SIZE;
                int offsetY = CELL_SIZE / 2 + GameConstant.UPPER_SIDE_HEIGHT.getValue();
                int y = baseY + offsetY;


                if (isSlotWithArc(row, col)) {
                    drawSlotWithArc(shapeRenderer, x, y);
                } else if (isSlot(row, col)) {
                    drawSlot(shapeRenderer, x, y);
                }
            }
        }
    }


    private void drawSlotWithArc(ShapeRenderer renderer, int x, int y) {
        renderer.begin(ShapeRenderer.ShapeType.Filled); // Ensure we use filled shape type
        renderer.setColor(Color.RED);
        renderer.circle(x, y, 9);
        renderer.end();
        // Uncomment and adjust if you need to draw arcs
        // renderer.setColor(Color.LIGHT_GRAY);
        // renderer.arc(x, y, 15, 0, 180);
        // renderer.setColor(Color.WHITE);
        // renderer.arc(x, y, 10, 0, 180);

    }

    private void drawSlot(ShapeRenderer renderer, int x, int y) {
        renderer.begin(ShapeRenderer.ShapeType.Filled); // Ensure we use filled shape type
        renderer.setColor(Color.BLACK);
        renderer.circle(x, y, 9);
        renderer.end();
    }

    private boolean isSlotWithArc(int row, int col) {
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

    private boolean isSlot(int row, int col) {
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


}
