package com.hammerox.rollingbal;

import com.badlogic.gdx.Gdx;
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
    private BitmapFont font;
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

    private boolean isGameOver = false;
    private int score = 0;

    @Override
    public void show() {
        Gdx.app.log(TAG, "show");

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = Util.generateFont(FONT_ROBOTO_PATH, SCORE_FONT_SIZE);
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
                font.setColor(Color.RED);
            } else {
                font.setColor(Color.BLACK);
            }
            
            viewport.getCamera().position.y -= CAMERA_SPEED * delta;
            cameraTop -= CAMERA_SPEED * delta;
            cameraBottom -= CAMERA_SPEED * delta;

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
        textButton.setText(font, String.valueOf(score));
        float textX = (viewport.getScreenWidth() - textButton.width) / 2;
        float textY = textButton.height + viewport.getScreenHeight() / 12;
        font.draw(batch, textButton, textX, textY);
        batch.end();

    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "dispose");
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
    }

    private void updateCameraConstants() {
        cameraTop = viewport.getCamera().position.y + worldHeight / 2;
        cameraBottom = viewport.getCamera().position.y - worldHeight / 2;
    }
}
