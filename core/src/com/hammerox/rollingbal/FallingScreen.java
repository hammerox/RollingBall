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


import java.util.LinkedList;
import java.util.List;

import static com.hammerox.rollingbal.Constants.*;

/**
 * Created by Mauricio on 27-Dec-16.
 */

public class FallingScreen extends ScreenAdapter {

    public final static String TAG = FallingScreen.class.getName();

    private ExtendViewport viewport;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont fontScore;
    private BitmapFont fontSpeech;
    private GlyphLayout textButton;

    private Character character;
    private float characterY;
    private List<Obstacle> allObstacles;

    private float cameraSpeed;
    private float cameraTopPosition;
    private float cameraBottomPosition;
    private float limitToBottomSize;
    private float limitToMiddleSize;
    private float worldHeight;
    private float lastObstaclePosition;

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

        character = new Character(viewport);
        allObstacles = new LinkedList<Obstacle>();

        resetGame();
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
                resetGame();
            }
        } else {
            // Update player
            character.update(delta);

            // Player-Platform collisions
            for (Obstacle obstacle : allObstacles) {
                character.landedOnPlatform(obstacle.getLeft());
                character.landedOnPlatform(obstacle.getRight());
            }
            characterY = character.getPosition().y;

            // Update camera
            boolean isCharacterBelowLimit = characterY < cameraBottomPosition + limitToBottomSize;
            if (isCharacterBelowLimit) {
                followCharacter();
                fontScore.setColor(Color.RED);
            } else {
                fontScore.setColor(Color.BLACK);
            }

            if (hasGameStarted)
                moveCamera(delta);

            // Create new obstacles, if necessary
            while (cameraBottomPosition - WORLD_SIZE < lastObstaclePosition) {
                lastObstaclePosition -= OBSTACLE_DISTANCE;
                allObstacles.add(Obstacle.newRandomObstacle(lastObstaclePosition));
            }

            // Remove obstacle from top, if necessary
            boolean isObstacleAboveScreen = allObstacles.get(0).getGapPosition().y > cameraTopPosition;
            if (isObstacleAboveScreen)
                allObstacles.remove(0);


            // Update score if better
            boolean isToUpdateScore = score < -characterY;
            if (isToUpdateScore)
                score = - Math.round(characterY);

            // End game if player lose
            boolean isCharacterAboveScreen = characterY - BALL_RADIUS > cameraTopPosition;
            if (isCharacterAboveScreen)
                isGameOver = true;
        }
    }

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

        // SHAPE RENDER
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

            // Render obstacles
        for (Obstacle obstacle : allObstacles) {
            obstacle.render(shapeRenderer);
        }

            // Render character
        character.render(shapeRenderer);

        shapeRenderer.end();

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

    private void moveCamera(float delta) {
        viewport.getCamera().position.y -= cameraSpeed * delta;
        cameraTopPosition -= cameraSpeed * delta;
        cameraBottomPosition -= cameraSpeed * delta;
    }

    private void followCharacter() {
        viewport.getCamera().position.y = characterY + limitToMiddleSize;
        updateCameraConstants();
    }

    private void updateCameraConstants() {
        cameraTopPosition = viewport.getCamera().position.y + worldHeight / 2;
        cameraBottomPosition = viewport.getCamera().position.y - worldHeight / 2;
    }

    private void startGame() {
        character.setFalling(true);
        hasGameStarted = true;
    }

    private void resetGame() {
        allObstacles.clear();

        character.init(WORLD_SIZE/2, 0);

        viewport.getCamera().position.y = viewport.getWorldHeight() / 2;
        updateCameraConstants();

        score = 0;
        lastObstaclePosition = 0;
        isGameOver = false;
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
}
