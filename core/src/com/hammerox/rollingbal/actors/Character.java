package com.hammerox.rollingbal.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.LinkedList;
import java.util.List;

import static com.hammerox.rollingbal.Constants.*;

/**
 * Created by Mauricio on 22-Dec-16.
 */

public class Character extends Actor implements InputProcessor{

    private boolean isGravityOn = false;
    private boolean isDead = false;
    private List<Actor> obstacles;


    public Character(float x, float y) {
        // TODO - Character's size is still hard coded. Refactor to Actor's standard.
        super(x,y, 0, 0);
        obstacles = new LinkedList<Actor>();
        Gdx.input.setInputProcessor(this);
    }

    public Character() {
        this(WORLD_SIZE/2, 0);
    }

    @Override
    public void move(float delta) {
        updateVelocity(delta);
        getPosition().mulAdd(getVelocity(), delta);
        checkCollision(this);
    }

    private void updateVelocity(float delta) {
        updateVelocityWithAccelerometer(delta);
        updateVelocityOnKeyboardInput();
        addGravityToVelocity(delta);
    }

    private void updateVelocityWithAccelerometer(float delta) {
        float accelerometer = -Gdx.input.getAccelerometerX();
        getVelocity().x = accelerometer * delta * ACCELEROMETER_FACTOR;
    }

    private void updateVelocityOnKeyboardInput() {
        boolean pressedA = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean pressedD = Gdx.input.isKeyPressed(Input.Keys.D);
        if (pressedA && pressedD) {
            getVelocity().x = 0;
        } else if (pressedA) {
            getVelocity().x = - BALL_INPUT_VELOCITY;
        } else if (pressedD) {
            getVelocity().x = BALL_INPUT_VELOCITY;
        }
    }

    private void addGravityToVelocity(float delta) {
        if (isGravityOn)
            getVelocity().mulAdd(WORLD_GRAVITY, delta);
    }

    @Override
    public void renderShape(ShapeRenderer shapeRenderer) {
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

    @Override
    public boolean checkCollision(Actor subject) {
        if (subject == this) {
            keepCharacterInsideScreen();
            boolean hasCollided = checkCollisionWithObstacles();
            if (hasCollided)
                return true;
        }
        return false;
    }

    @Override
    public void onCollision(Actor subject) {
        if (subject instanceof Platform) {
            adjustMovementOnCollision(subject.getTop());
            boolean isDeadly = ((Platform) subject).isDeadly();
            if (isDeadly)
                isDead = true;
        }
    }

    private void keepCharacterInsideScreen() {
        if (getPosition().x - BALL_RADIUS < 0) {
            getPosition().x = BALL_RADIUS;
        } else if (getPosition().x + BALL_RADIUS > WORLD_SIZE) {
            getPosition().x = WORLD_SIZE - BALL_RADIUS;
        }
    }

    private boolean checkCollisionWithObstacles() {
        if (obstacles != null) {
            for (Actor obstacle : obstacles) {
                boolean hasCollided = obstacle.checkCollision(this);
                if (hasCollided) return true;
            }
        }
        return false;
    }

    private void adjustMovementOnCollision(float y) {
        getVelocity().y = - getVelocity().y * BALL_BOUNCE_ABSORPTION;
        getPosition().y = y + BALL_RADIUS;
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
    public boolean isGravityOn() {
        return isGravityOn;
    }

    public void setGravityOn(boolean gravityOn) {
        isGravityOn = gravityOn;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public List<Actor> getObstacles() {
        return obstacles;
    }

    public void setObstacles(List<Actor> obstacles) {
        this.obstacles = obstacles;
    }
}
