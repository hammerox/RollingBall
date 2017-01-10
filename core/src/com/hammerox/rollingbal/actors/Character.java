package com.hammerox.rollingbal.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;


import java.util.List;

import static com.hammerox.rollingbal.Constants.*;
import static com.hammerox.rollingbal.Util.removeImprecision;

/**
 * Created by Mauricio on 22-Dec-16.
 */

public class Character extends Actor implements InputProcessor{

//    TODO - Remove these variables.
    private Vector2 position = getPosition();
    private Vector2 velocity = getVelocity();
    private Vector2 lastPosition = getLastPosition();
    private Vector2 lastVelocity = getLastVelocity();

    private boolean isFalling = false;


    public Character(float x, float y) {
        // TODO - Character's size is still hard coded. Refactor to Actor's standard.
        super(x,y, 0, 0);
        Gdx.input.setInputProcessor(this);
    }


    public void init(float x, float y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);

        lastPosition = position.cpy();
        lastVelocity = velocity.cpy();
    }

    @Override
    public void move(float delta) {
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

    @Override
    public void renderShape(ShapeRenderer shapeRenderer) {
        // RENDER
        shapeRenderer.setColor(0.5f, 0, 0, 1);
        shapeRenderer.circle(position.x, position.y, BALL_RADIUS, 50);
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

        return null;
    }

    /*
    * NON USED INTERFACE METHODS
    * */
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /*
    GETTERS AND SETTERS
    */
    public boolean isFalling() {
        return isFalling;
    }

    public void setFalling(boolean falling) {
        isFalling = falling;
    }
}
