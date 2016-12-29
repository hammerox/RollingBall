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

    private float cameraTop;
    private float cameraBottom;
    private float limitToBottomSize;
    private float limitToMiddleSize;
    private float worldHeight;
    private float lastObstacleHeight;

    private boolean hasGameStarted = false;
    private boolean isGameOver = false;
    private int score = 0;

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
        character.init(WORLD_SIZE/2, WORLD_SIZE/2);

        allObstacles = new LinkedList<Obstacle>();
        allObstacles.add(Obstacle.newRandomObstacle(0*OBSTACLE_DISTANCE));

        lastObstacleHeight = 0;
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

    @Override
    public void render(float delta) {
        // TODO - Uncomment delta to debug
        delta = 1.0f / 60.0f;

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        // Ready to start game
        if (!hasGameStarted) {
            if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                startGame();
            }
        }

        if (!isGameOver) {
            // UPDATE
                // Update player
            character.update(delta);

                // Player-Platform collisions
            for (Obstacle obstacle : allObstacles) {
                character.landedOnPlatform(obstacle.getLeft());
                character.landedOnPlatform(obstacle.getRight());
            }
            characterY = character.getPosition().y;

                // Update camera
            if (characterY < cameraBottom + limitToBottomSize) {
                viewport.getCamera().position.y = characterY + limitToMiddleSize;
                updateCameraConstants();
                fontScore.setColor(Color.RED);
            } else {
                fontScore.setColor(Color.BLACK);
            }

            if (hasGameStarted) {
                viewport.getCamera().position.y -= CAMERA_SPEED * delta;
                cameraTop -= CAMERA_SPEED * delta;
                cameraBottom -= CAMERA_SPEED * delta;
            }

                // Create new obstacles, if necessary
            while (cameraBottom - WORLD_SIZE < lastObstacleHeight) {
                lastObstacleHeight -= OBSTACLE_DISTANCE;
                allObstacles.add(Obstacle.newRandomObstacle(lastObstacleHeight));
            }

                // Remove obstacles, if necessary
            float y = allObstacles.get(0).getGapPosition().y;
            if (y > cameraTop) {
                allObstacles.remove(0);
            }

                // Update score if better
            if (-characterY > score) score = - Math.round(characterY);

                // End game if player lose
            if (characterY - BALL_RADIUS > cameraTop) {
                isGameOver = true;
            }
        }

        // RENDER
            // Shape render
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (Obstacle obstacle : allObstacles) {
            obstacle.render(shapeRenderer);
        }

        character.render(shapeRenderer);

        shapeRenderer.end();

            // Sprite render
        batch.begin();
            // Start message
        if (!hasGameStarted) {
            fontSpeech.setColor(Color.BLACK);
            textButton.setText(fontSpeech, "Tap to start");
            float textX = (viewport.getScreenWidth() - textButton.width) / 2;
            float textY = (viewport.getScreenHeight() + textButton.height) / 2;
            fontSpeech.draw(batch, textButton, textX, textY);
        }

            // Show score or game-over message
        if (isGameOver) {
            textButton.setText(fontSpeech, String.valueOf(score));
            float textX = (viewport.getScreenWidth() - textButton.width) / 2;
            float textY = viewport.getScreenHeight() / 2 + textButton.height;
            fontSpeech.draw(batch, textButton, textX, textY);

            textButton.setText(fontSpeech, "AWESOME!");
            textX = (viewport.getScreenWidth() - textButton.width) / 2;
            textY = viewport.getScreenHeight() / 2 - textButton.height;
            fontSpeech.draw(batch, textButton, textX, textY);
        } else {
            textButton.setText(fontScore, String.valueOf(score));
            float textX = (viewport.getScreenWidth() - textButton.width) / 2;
            float textY = textButton.height + viewport.getScreenHeight() / 12;
            fontScore.draw(batch, textButton, textX, textY);
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

    private void updateCameraConstants() {
        cameraTop = viewport.getCamera().position.y + worldHeight / 2;
        cameraBottom = viewport.getCamera().position.y - worldHeight / 2;
    }

    private void startGame() {
        character.setFalling(true);
        hasGameStarted = true;
    }
}
