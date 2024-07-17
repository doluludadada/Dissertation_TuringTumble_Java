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
import com.badlogic.gdx.physics.box2d.*;
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
        camera.setToOrtho(false, 800, 980);
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

        createCollisionLines();
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

//        // Draw background
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.rect(0, 0, width, height, Color.WHITE, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY);
//        shapeRenderer.end();

//        // Draw border
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(Color.BLACK);
//        Gdx.gl.glLineWidth(50);
//        shapeRenderer.rect(0, 0, width, height);
//        shapeRenderer.end();

        drawBoardLine();
        drawBoardSlot();


    }


    private void drawBoardLine() {
        float width = camera.viewportWidth;
        float height = camera.viewportHeight;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        Gdx.gl.glLineWidth(6);


        float[][] Lines = getLine(width, height);


        for (float[] line : Lines) {
            shapeRenderer.line(line[0], line[1], line[2], line[3]);
        }


        shapeRenderer.end();
    }

    private void createCollisionLines() {
        float width = camera.viewportWidth;
        float height = camera.viewportHeight;

        float[][] lines = getLine(width, height);

        for (float[] line : lines) {
            createEdge(line[0], line[1], line[2], line[3]);
        }
    }


    private float[][] getLine(float width, float height) {
    /*
      float x, float y, float x2, float y2      x1-x2, y1-y2
    */
        float[][] Lines = {
            {width / 2, height, width / 2, height - 50},                                                // 上中線
            {width / 2, height - 50, width / 2 - CELL_SIZE * 4, height - 100},                          // 上左斜線
            {0, height - 100, (width / 2) - CELL_SIZE * 2.3f, height - CELL_SIZE * 3},                  // 左下斜線
            {width / 2, height - 50, width / 2 + CELL_SIZE * 4, height - 100},                          // 右上斜線
            {width, height - 100, (width / 2) + CELL_SIZE * 2.3f, height - CELL_SIZE * 3},              // 右下斜線
            {width, 2 * CELL_SIZE, ((width / 2) + CELL_SIZE * 0.8f), CELL_SIZE},                        // 右下斜線
            {0, 2 * CELL_SIZE, ((width / 2) - CELL_SIZE * 0.8f), CELL_SIZE},                            // 左下斜線
        };
        return Lines;
    }

    private void drawBoardSlot() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); // Start batch once

        float offsetX = (camera.viewportWidth - SLOT_NUMBER_WIDTH * CELL_SIZE) / 2;
        float offsetY = (camera.viewportHeight - SLOT_NUMBER_HEIGHT * CELL_SIZE) / 2;


        for (int row = 0; row < SLOT_NUMBER_HEIGHT; row++) {
            for (int col = 0; col < SLOT_NUMBER_WIDTH; col++) {
                float x = col * CELL_SIZE + offsetX + CELL_SIZE / 2;

                // Flipping the row index vertically
                int flippedRow = SLOT_NUMBER_HEIGHT - row - 1;
                // Calculating the y-coordinate for the slot
                int baseY = flippedRow * CELL_SIZE;
                float y = baseY + +offsetY;


                if (isSlotWithArc(row, col)) {
                    drawSlotWithArc(shapeRenderer, x, y);
                } else if (isSlot(row, col)) {


                    drawSlot(shapeRenderer, x, y);
                }
            }
        }
        shapeRenderer.end();

    }

    private void drawSlotWithArc(ShapeRenderer renderer, float x, float y) {

        renderer.setColor(Color.GRAY);
        renderer.circle(x, y, 10);

        renderer.setColor(Color.LIGHT_GRAY);
        renderer.arc(x, y - 11, 11, 180, 180);

        renderer.setColor(Color.WHITE);
        renderer.arc(x, y - 10, 6, 180, 180);

    }

    private void drawSlot(ShapeRenderer renderer, float x, float y) {
        renderer.setColor(Color.GRAY);
        renderer.circle(x, y, 10);

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

    private void createEdge(float x1, float y1, float x2, float y2) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = GameManager.getInstance().getWorld().createBody(bodyDef);

        EdgeShape shape = new EdgeShape();
        shape.set(x1, y1, x2, y2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.0f;

        body.createFixture(fixtureDef);
        shape.dispose();

    }


}
