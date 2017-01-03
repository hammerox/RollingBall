package com.hammerox.rollingbal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;


import java.util.List;

import static com.hammerox.rollingbal.Constants.*;
import static com.hammerox.rollingbal.Util.removeImprecision;

/**
 * Created by Mauricio on 22-Dec-16.
 */

public class Character extends InputAdapter {

    private Vector2 position;
    private Vector2 velocity;

    private Vector2 lastPosition;
    private Vector2 lastVelocity;

    private boolean isFalling = false;
    private boolean isLanded = false;


    public Character() {
        Gdx.input.setInputProcessor(this);
    }


    public void init(float x, float y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);

        lastPosition = position.cpy();
        lastVelocity = velocity.cpy();
    }


    public void update(float delta) {
        // INPUT RESPONSE
            // Accelerometer
        float accelerometer;

        accelerometer = -Gdx.input.getAccelerometerX();
        velocity.x = accelerometer * delta * ACCELEROMETER_FACTOR;

            // A
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocity.x = - BALL_INPUT_VELOCITY;
        }
            // D
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.x = BALL_INPUT_VELOCITY;
        }

        // UPDATE
            // Adding gravity
        if (isFalling)
            velocity.mulAdd(WORLD_GRAVITY, delta);

            // Position
        position.mulAdd(velocity, delta);

        // COLLISIONS
            // With walls
        if (position.x - BALL_RADIUS < 0) {
            position.x = BALL_RADIUS;
        } else if (position.x + BALL_RADIUS > WORLD_SIZE) {
            position.x = WORLD_SIZE - BALL_RADIUS;
        }
    }


    public void render(ShapeRenderer shapeRenderer) {
        // FINISH UPDATING
        lastPosition.set(position);
        lastVelocity.set(velocity);

        // RENDER
        shapeRenderer.setColor(0.5f, 0, 0, 1);
        shapeRenderer.circle(position.x, position.y, BALL_RADIUS, 50);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.D:
                velocity.x = 0;
                break;
        }
        return false;
    }

    /** Update character's position when landed on something.
     *
     * @param y = Is the surface's Y position which the character has landed.
     */
    public void land(float y) {
        velocity.y = - velocity.y * BALL_BOUNCE_ABSORPTION;
        position.y = y + BALL_RADIUS;
        isLanded = true;
    }

    /** Check if character has landed on a platform.
     *
     * The check is made from character's last position and current position.
     * If last position was above the platform...
     * and current position is inside the platform...
     * then character's current position is adjusted.
     *
     * It was necessary to remove some decimal precision to avoid truncation error.
     */
    public boolean landedOnPlatform(Platform platform) {
        float yBefore = lastPosition.y - BALL_RADIUS;
        float yAfter = position.y - BALL_RADIUS;
        float top = platform.top;

        boolean wasOver = removeImprecision(yBefore) >= removeImprecision(top);
        boolean isUnder = yAfter < top;
        boolean isInside = position.x >= platform.position.x
                && position.x <= platform.position.x + platform.size.x;

        if (wasOver && isUnder && isInside) {
            land(platform.top);
            return true;
        }

        return false;
    }

    /** Search on all obstacles if character has landed on any platforms.
     *
     * @return if character has landed, it returns the landed platform.
     * if not, it returns null.
     */
    public Platform getLandedPlatform(List<Obstacle> obstacleList) {
        for (Obstacle obstacle : obstacleList) {
            if (landedOnPlatform(obstacle.getLeft())) {
                return obstacle.getLeft();
            }

            if (landedOnPlatform(obstacle.getRight())) {
                return obstacle.getRight();
            }
        }

        isLanded = false;
        return null;
    }


    /*
    GETTERS AND SETTERS
    */

    public Vector2 getLastPosition() {
        return lastPosition;
    }

    public Vector2 getLastVelocity() {
        return lastVelocity;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public void setFalling(boolean falling) {
        isFalling = falling;
    }
}
