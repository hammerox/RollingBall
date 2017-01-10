package com.hammerox.rollingbal.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


import java.util.List;

import static com.hammerox.rollingbal.Constants.*;
import static com.hammerox.rollingbal.Util.removeImprecision;

/**
 * Created by Mauricio on 22-Dec-16.
 */

public class Character extends Actor implements InputProcessor{

    private boolean isFalling = false;


    public Character(float x, float y) {
        // TODO - Character's size is still hard coded. Refactor to Actor's standard.
        super(x,y, 0, 0);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void move(float delta) {
        // INPUT RESPONSE
        // Accelerometer
        float accelerometer;

        accelerometer = -Gdx.input.getAccelerometerX();
        getVelocity().x = accelerometer * delta * ACCELEROMETER_FACTOR;

        // A
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            getVelocity().x = - BALL_INPUT_VELOCITY;
        }
        // D
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            getVelocity().x = BALL_INPUT_VELOCITY;
        }

        // UPDATE
        // Adding gravity
        if (isFalling)
            getVelocity().mulAdd(WORLD_GRAVITY, delta);

        // Position
        getPosition().mulAdd(getVelocity(), delta);

        // COLLISIONS
        // With walls
        if (getPosition().x - BALL_RADIUS < 0) {
            getPosition().x = BALL_RADIUS;
        } else if (getPosition().x + BALL_RADIUS > WORLD_SIZE) {
            getPosition().x = WORLD_SIZE - BALL_RADIUS;
        }
    }

    @Override
    public void renderShape(ShapeRenderer shapeRenderer) {
        // RENDER
        shapeRenderer.setColor(0.5f, 0, 0, 1);
        shapeRenderer.circle(getPosition().x, getPosition().y, BALL_RADIUS, 50);
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.D:
                getVelocity().x = 0;
                break;
        }
        return false;
    }

    /** Update character's position when landed on something.
     *
     * @param y = Is the surface's Y position which the character has landed.
     */
    public void land(float y) {
        getVelocity().y = - getVelocity().y * BALL_BOUNCE_ABSORPTION;
        getPosition().y = y + BALL_RADIUS;
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
        float yBefore = getLastPosition().y - BALL_RADIUS;
        float yAfter = getPosition().y - BALL_RADIUS;
        float top = platform.getTop();

        boolean wasOver = removeImprecision(yBefore) >= removeImprecision(top);
        boolean isUnder = yAfter < top;
        boolean isInside = getPosition().x >= platform.getPosition().x
                && getPosition().x <= platform.getPosition().x + platform.getSize().x;

        if (wasOver && isUnder && isInside) {
            land(platform.getTop());
            return true;
        }

        return false;
    }

    /** Search on all obstacles if character has landed on any platforms.
     *
     * @return if character has landed, it returns the landed platform.
     * if not, it returns null.
     */
    public Platform getLandedPlatform(List<DoublePlatform> doublePlatformList) {
        for (DoublePlatform doublePlatform : doublePlatformList) {
            if (landedOnPlatform(doublePlatform.getLeftPlat())) {
                return doublePlatform.getLeftPlat();
            }

            if (landedOnPlatform(doublePlatform.getRightPlat())) {
                return doublePlatform.getRightPlat();
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
