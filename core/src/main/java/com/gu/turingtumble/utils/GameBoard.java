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
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gu.turingtumble.MainGame;
import com.gu.turingtumble.components.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gu.turingtumble.game.ui.*;

import java.util.*;


public class GameBoard implements Screen, ContactListener {


    private final GameUIManager uiManager;
    private final OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private Box2DDebugRenderer debugRenderer;
    private final Viewport viewport;
    private final SpriteBatch batch;


    private final int SLOT_NUMBER_WIDTH = GameConstant.SLOT_NUMBER_WIDTH.get();
    private final int SLOT_NUMBER_HEIGHT = GameConstant.SLOT_NUMBER_HEIGHT.get();
    private final int CELL_SIZE = GameConstant.CELL_SIZE.get();


    public GameBoard(MainGame game) {
        this.uiManager = game.getUiManager();
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConstant.WINDOW_WIDTH.get(), GameConstant.WINDOW_HEIGHT.get(), camera);
        initialiseRenderers();
        batch = new SpriteBatch();

        Gdx.gl.glEnable(GL20.GL_BLEND);                                                                                 //anti-aliasing
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        draw();
    }


    @Override
    public void show() {
        GameManager.getWorld().setContactListener(this);
        Gdx.input.setInputProcessor(uiManager.getUiStage());
        uiManager.showGameUI();


    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        createCollisionLines();
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
        shapeRenderer.dispose();
        debugRenderer.dispose();
        batch.dispose();
        if (GameManager.getWorld() != null) {
            GameManager.getWorld().dispose();
        }

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }


    private void update(float delta) {
        GameManager.updateGameLogic(delta);
        handleInput();
        camera.update();

    }

    private void initialiseRenderers() {
        shapeRenderer = new ShapeRenderer();
        debugRenderer = new Box2DDebugRenderer();
    }

    private void handleInput() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            handleBallStopperClick(touchPos);
            if (!GameManager.switchComponentFunction(new Vector2(touchPos.x, touchPos.y))) {
                GameManager.addComponent(new Vector2(touchPos.x, touchPos.y));
            }
            uiManager.updateUI();

            System.out.println(touchPos.x + " " + touchPos.y);
        }
    }


    private void handleBallStopperClick(Vector3 touchPos) {
        BallStopper redBallStopper = GameManager.getRedBallStopper();
        BallStopper blueBallStopper = GameManager.getBlueBallStopper();
        int allowedStopper = GameManager.getGameState().getAllowedBallStopper();

        if (isAllowedAndTouched(allowedStopper, 1, touchPos, redBallStopper)) {
            redBallStopper.launchBall();
            return;
        }
        if (isAllowedAndTouched(allowedStopper, 0, touchPos, blueBallStopper)) {
            blueBallStopper.launchBall();
        }
    }

    private boolean isAllowedAndTouched(int allowedStopper, int stopperType, Vector3 touchPos, BallStopper stopper) {
        return (allowedStopper == -1 || allowedStopper == stopperType) &&
            stopper != null && isTouchingBallStopper(touchPos, stopper);
    }


    private boolean isTouchingBallStopper(Vector3 touchPos, BallStopper stopper) {
        Vector2 stopperPos = stopper.getBody().getPosition();
        float touchRadius = BallStopper.RADIUS * 1.5f;
        return stopperPos.dst(new Vector2(touchPos.x, touchPos.y)) < touchRadius;
    }


    private void draw() {
        viewport.apply();
//        set background to white
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(1, 1, 1, 0);       //white

        shapeRenderer.setProjectionMatrix(camera.combined);
        drawBoard();
        drawBallStoppers();
        drawComponents();
        drawBottomSensor();
        drawBalls();
        debugRenderer.render(GameManager.getWorld(), camera.combined);
    }

    private void drawBalls() {
        for (Ball ball : GameManager.getRedBalls()) {
            ball.draw(shapeRenderer);
        }
        for (Ball ball : GameManager.getBlueBalls()) {
            ball.draw(shapeRenderer);
        }
    }


    private void drawBoard() {

//        // Draw background
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0, 0, GameConstant.UI_WIDTH.get(), GameConstant.WINDOW_HEIGHT.get(), Color.WHITE, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY);
        shapeRenderer.end();

        // Draw border
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.DARK_GRAY);
        Gdx.gl.glLineWidth(10);
        shapeRenderer.rect(GameConstant.UI_WIDTH.get(), 0, GameConstant.GAME_WIDTH.get(), GameConstant.WINDOW_HEIGHT.get());
        shapeRenderer.end();

        drawBoardLine();
        drawBoardSlot();
    }


    private void drawBoardLine() {

        float width = camera.viewportWidth + GameConstant.UI_WIDTH.get();
        float height = camera.viewportHeight;


        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.DARK_GRAY);
        Gdx.gl.glLineWidth(6);


        float[][] Lines = getLine(width, height);

        for (float[] line : Lines) {
            shapeRenderer.line(line[0], line[1], line[2], line[3]);
        }


        shapeRenderer.end();
    }

    private void createCollisionLines() {

        float width = camera.viewportWidth + GameConstant.UI_WIDTH.get();
        float height = camera.viewportHeight;

        float[][] lines = getLine(width, height);

        for (float[] line : lines) {
            createEdge(line[0], line[1], line[2], line[3]);
        }

        //border lines
        createEdge(GameConstant.UI_WIDTH.get(), 0, GameConstant.UI_WIDTH.get(), GameConstant.WINDOW_HEIGHT.get()); // left border
        createEdge(GameConstant.UI_WIDTH.get(), GameConstant.WINDOW_HEIGHT.get(), GameConstant.WINDOW_WIDTH.get(), GameConstant.WINDOW_HEIGHT.get()); // top border
        createEdge(GameConstant.WINDOW_WIDTH.get(), GameConstant.WINDOW_HEIGHT.get(), GameConstant.WINDOW_WIDTH.get(), 0); // right border
        createEdge(GameConstant.WINDOW_WIDTH.get(), 0, GameConstant.UI_WIDTH.get(), 0); // bottom border

    }


    private float[][] getLine(float width, float height) {
    /*
      float x, float y, float x2, float y2      x1-x2, y1-y2
    */
        float[][] Lines = {{width / 2, height, width / 2, height - 50},                                                 // 上中線
            {width / 2, height - 50, width / 2 - CELL_SIZE * 6, height - 1.4f * CELL_SIZE},                             // 上左斜線
            {width / 2, height - 50, width / 2 + CELL_SIZE * 6, height - 1.4f * CELL_SIZE},                             // 右上斜線
            {(GameConstant.UI_WIDTH.get()), (height - 150), ((width / 2) - CELL_SIZE * 2.45F), (height - CELL_SIZE * 3.15f)},         // 左下斜線
            {width, height - 50, ((width / 2) + CELL_SIZE * 2.45f), (height - CELL_SIZE * 3.15f)},                                        // 右下斜線
            {width, 2 * CELL_SIZE, ((width / 2) + CELL_SIZE * 0.8f), CELL_SIZE},                                        // 右下斜線
            {GameConstant.UI_WIDTH.get(), 2 * CELL_SIZE, ((width / 2) - CELL_SIZE * 0.8f), CELL_SIZE},                  // 左下斜線
        };
        return Lines;
    }

    private void drawBoardSlot() {
        float width = camera.viewportWidth + GameConstant.UI_WIDTH.get();
        float height = camera.viewportHeight;

        float offsetX = (width - SLOT_NUMBER_WIDTH * CELL_SIZE) / 2;
        float offsetY = (height - SLOT_NUMBER_HEIGHT * CELL_SIZE) / 2;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int row = 0; row < SLOT_NUMBER_HEIGHT; row++) {
            for (int col = 0; col < SLOT_NUMBER_WIDTH; col++) {

                float x = col * CELL_SIZE + offsetX + (CELL_SIZE / 2f);

                // row index vertically
                int flippedRow = SLOT_NUMBER_HEIGHT - row - 1;
                // Calculating the y-coordinate for the slot
                int baseY = flippedRow * CELL_SIZE;

                float y = baseY + +offsetY;

                if (GameManager.isSlotWithArc(row, col)) {
                    drawSlotWithArc(shapeRenderer, x, y);
                    GameManager.addSlotPosition(new Vector2(x, y), true);
                } else if (GameManager.isSlot(row, col)) {
                    drawSlot(shapeRenderer, x, y);
                    GameManager.addSlotPosition(new Vector2(x, y), false);
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


    private void drawComponents() {
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        for (Map.Entry<Vector2, GameComponents> entry : GameManager.getComponents().entrySet()) {
            GameComponents component = entry.getValue();
            Vector2 position = entry.getKey();
            component.draw(batch, position.x, position.y);
        }
        batch.end();
    }

    private void drawBallStoppers() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        BallStopper redBallStopper = GameManager.getRedBallStopper();
        BallStopper blueBallStopper = GameManager.getBlueBallStopper();

        if (redBallStopper != null) {
            redBallStopper.draw(shapeRenderer);
        }
        if (blueBallStopper != null) {
            blueBallStopper.draw(shapeRenderer);
        }
    }

    private void drawBottomSensor() {
        BottomSensor bottomSensor = GameManager.getBottomSensor();
        if (bottomSensor != null) {
            bottomSensor.draw(shapeRenderer);
        }
    }

    private void createEdge(float x1, float y1, float x2, float y2) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = GameManager.getWorld().createBody(bodyDef);

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


    public float getCameraWidth() {
        return camera.viewportWidth;
    }

    public float getCameraHeight() {
        return camera.viewportHeight;
    }

    public Box2DDebugRenderer getDebugRenderer() {
        return debugRenderer;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public GameUIManager getUiManager() {
        return uiManager;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();
        Object userDataA = bodyA.getUserData();
        Object userDataB = bodyB.getUserData();

        if (userDataA instanceof Ramp && userDataB instanceof Ball) {
            Ramp ramp = (Ramp) userDataA;
            ramp.beginContact(bodyB);
        }
        else if (userDataA instanceof BallStopper && userDataB instanceof Ball) {
            BallStopper ballStopper = (BallStopper) userDataA;
            ballStopper.handleContact(bodyB);
        } else if (userDataA instanceof BottomSensor && userDataB instanceof Ball) {
            BottomSensor bottomSensor = (BottomSensor) userDataA;
            bottomSensor.handleContact(bodyB, true);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();
        Object userDataA = bodyA.getUserData();
        Object userDataB = bodyB.getUserData();

        if (userDataA instanceof Ramp && userDataB instanceof Ball) {
            Ramp ramp = (Ramp) userDataA;
            ramp.endContact(bodyB);
        }
    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
