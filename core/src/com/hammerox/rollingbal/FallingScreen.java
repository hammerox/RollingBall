package com.hammerox.rollingbal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.hammerox.rollingbal.actors.Actor;


import static com.hammerox.rollingbal.Constants.*;

/**
 * Created by Mauricio on 27-Dec-16.
 */

public abstract class FallingScreen extends ScreenAdapter {

    public final static String TAG = FallingScreen.class.getName();

    private ExtendViewport viewport;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont fontScore;
    private BitmapFont fontSpeech;
    private GlyphLayout textButton;

    private float cameraSpeed;
    private float cameraTopPosition;
    private float cameraBottomPosition;
    private float limitToBottomSize;
    private float limitToMiddleSize;
    private float worldHeight;

    private boolean hasGameStarted = false;
    private boolean isGameOver = false;
    private int score;


    public FallingScreen(float cameraSpeed) {
        this.cameraSpeed = cameraSpeed;
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "show");

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        fontScore = Util.generateFont(FONT_ROBOTO_PATH, SCORE_FONT_SIZE);
        fontSpeech = Util.generateFont(FONT_ROBOTO_PATH, SCREEN_FONT_SIZE);
        textButton = new GlyphLayout();

        viewport = new ExtendViewport(WORLD_SIZE, WORLD_SIZE);

        newGame();
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "resize");
        viewport.update(width, height, true);

        viewport.getCamera().position.x = WORLD_SIZE/2;
        updateCameraConstants();

        worldHeight = viewport.getWorldHeight();
        limitToBottomSize = CAMERA_LIMIT_RATIO * worldHeight;
        limitToMiddleSize = (0.5f - CAMERA_LIMIT_RATIO) * worldHeight ;
    }

    public void update(float delta) {
        if (!hasGameStarted) {
            if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                // Start game on first touch
                startGame();
            }
        }

        if (isGameOver) {
            if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                // Restart game on touch
                newGame();
            }
        } else {
            updateActors(delta);
            isGameOver = gameOverCondition();
        }
    }

    public abstract boolean gameOverCondition();
    public abstract void updateActors(float delta);
    public abstract void renderActors();

    @Override
    public void render(float delta) {
        // TODO - Uncomment delta to debug
        delta = 1.0f / 60.0f;

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        // UPDATE
        update(delta);

        renderActors();

        // SPRITE RENDER
        batch.begin();
            // Start message
        if (!hasGameStarted) {
            showStartMessage();
        }

            // Show score or game-over message
        if (isGameOver) {
            showGameOverMessage();
        } else {
            showScore();
        }

        batch.end();
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "dispose");
        shapeRenderer.dispose();
        batch.dispose();
        fontScore.dispose();
    }

    void moveCameraWithActor(float delta, Actor actor) {
        followActorOnLimit(actor);
        moveCamera(delta);
    }

    void moveCamera(float delta) {
        if (hasGameStarted) {
            viewport.getCamera().position.y -= cameraSpeed * delta;
            cameraTopPosition -= cameraSpeed * delta;
            cameraBottomPosition -= cameraSpeed * delta;
        }
    }

    void followActorOnLimit(Actor actor) {
        boolean isActorBelowLimit = actor.getPosition().y < cameraBottomPosition + limitToBottomSize;
        if (isActorBelowLimit) {
            viewport.getCamera().position.y = actor.getPosition().y + limitToMiddleSize;
            updateCameraConstants();
            getFontScore().setColor(Color.RED);
        } else {
            getFontScore().setColor(Color.BLACK);
        }
    }

    void updateCameraConstants() {
        cameraTopPosition = viewport.getCamera().position.y + worldHeight / 2;
        cameraBottomPosition = viewport.getCamera().position.y - worldHeight / 2;
    }

    void newGame() {
        viewport.getCamera().position.y = viewport.getWorldHeight() / 2;
        updateCameraConstants();
        score = 0;
        isGameOver = false;
        hasGameStarted = false;
    }

    void startGame() {
        hasGameStarted = true;
    }

    private void showStartMessage() {
        fontSpeech.setColor(Color.BLACK);
        textButton.setText(fontSpeech, "Tap to start");
        float textX = (viewport.getScreenWidth() - textButton.width) / 2;
        float textY = (viewport.getScreenHeight() + textButton.height) / 2;
        fontSpeech.draw(batch, textButton, textX, textY);
    }

    private void showGameOverMessage() {
        textButton.setText(fontSpeech, String.valueOf(score));
        float textX = (viewport.getScreenWidth() - textButton.width) / 2;
        float textY = viewport.getScreenHeight() / 2 + textButton.height;
        fontSpeech.draw(batch, textButton, textX, textY);

        textButton.setText(fontSpeech, "AWESOME!");
        textX = (viewport.getScreenWidth() - textButton.width) / 2;
        textY = viewport.getScreenHeight() / 2 - textButton.height;
        fontSpeech.draw(batch, textButton, textX, textY);
    }

    private void showScore() {
        textButton.setText(fontScore, String.valueOf(score));
        float textX = (viewport.getScreenWidth() - textButton.width) / 2;
        float textY = textButton.height + viewport.getScreenHeight() / 12;
        fontScore.draw(batch, textButton, textX, textY);
    }


    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public float getCameraBottomPosition() {
        return cameraBottomPosition;
    }

    public void setCameraBottomPosition(float cameraBottomPosition) {
        this.cameraBottomPosition = cameraBottomPosition;
    }

    public float getCameraSpeed() {
        return cameraSpeed;
    }

    public void setCameraSpeed(float cameraSpeed) {
        this.cameraSpeed = cameraSpeed;
    }

    public float getCameraTopPosition() {
        return cameraTopPosition;
    }

    public void setCameraTopPosition(float cameraTopPosition) {
        this.cameraTopPosition = cameraTopPosition;
    }

    public BitmapFont getFontScore() {
        return fontScore;
    }

    public void setFontScore(BitmapFont fontScore) {
        this.fontScore = fontScore;
    }

    public BitmapFont getFontSpeech() {
        return fontSpeech;
    }

    public void setFontSpeech(BitmapFont fontSpeech) {
        this.fontSpeech = fontSpeech;
    }

    public boolean hasGameStarted() {
        return hasGameStarted;
    }

    public void setHasGameStarted(boolean hasGameStarted) {
        this.hasGameStarted = hasGameStarted;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public float getLimitToBottomSize() {
        return limitToBottomSize;
    }

    public void setLimitToBottomSize(float limitToBottomSize) {
        this.limitToBottomSize = limitToBottomSize;
    }

    public float getLimitToMiddleSize() {
        return limitToMiddleSize;
    }

    public void setLimitToMiddleSize(float limitToMiddleSize) {
        this.limitToMiddleSize = limitToMiddleSize;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    public static String getTAG() {
        return TAG;
    }

    public GlyphLayout getTextButton() {
        return textButton;
    }

    public void setTextButton(GlyphLayout textButton) {
        this.textButton = textButton;
    }

    public ExtendViewport getViewport() {
        return viewport;
    }

    public void setViewport(ExtendViewport viewport) {
        this.viewport = viewport;
    }

    public float getWorldHeight() {
        return worldHeight;
    }

    public void setWorldHeight(float worldHeight) {
        this.worldHeight = worldHeight;
    }
}
