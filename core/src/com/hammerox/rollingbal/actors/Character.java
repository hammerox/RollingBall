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
        if (isGravityOn)
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

        checkCollision(this);
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
            if (obstacles != null) {
                for (Actor actor : obstacles) {
                    boolean hasCollided = actor.checkCollision(this);
                    if (hasCollided) return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onCollision(Actor subject) {
        if (subject instanceof Platform) {
            land(subject.getTop());
            if (((Platform) subject).isDeadly())
                isDead = true;
        }
    }

    public void addObstacle(Actor obstacle) {
        obstacles.add(obstacle);
    }

    public void removeObstacle(Actor obstacle) {
        obstacles.remove(obstacle);
    }

    /** Update character's position when landed on something.
     *
     * @param y = Is the surface's Y position which the character has landed.
     */
    public void land(float y) {
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
