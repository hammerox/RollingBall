package com.hammerox.rollingbal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Mauricio on 22-Dec-16.
 */

public class Ball extends InputAdapter {

    private Viewport viewport;

    private Vector2 position;
    private Vector2 velocity;
    private boolean isJumping = false;

    public Ball(Viewport viewport) {
        this.viewport = viewport;

        Gdx.input.setInputProcessor(this);
    }

    public void init() {
        position = new Vector2(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2);
        velocity = new Vector2(0, 0);
    }

    public void render(float delta, ShapeRenderer shapeRenderer) {

        // INPUT RESPONSE
            // A
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocity.x = - Constants.BALL_INPUT_VELOCITY;
        }
            // D
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.x = Constants.BALL_INPUT_VELOCITY;
        }
            // Accelerometer
        float accelerometer = Gdx.input.getAccelerometerY();
        velocity.x = accelerometer * delta * Constants.ACCELEROMETER_FACTOR;

        // UPDATE
            // Jump status
        if (isJumping && velocity.y <= 0) isJumping = false;

            // Position
        velocity.mulAdd(Constants.WORLD_GRAVITY, delta);
        position.mulAdd(velocity, delta);

        // COLLISIONS
            // With walls
        if (position.x - Constants.BALL_RADIUS < 0) {
            position.x = Constants.BALL_RADIUS;
        } else if (position.x + Constants.BALL_RADIUS > viewport.getWorldWidth()) {
            position.x = viewport.getWorldWidth() - Constants.BALL_RADIUS;
        }

            // With ground
        if (position.y < 0) {
            velocity.y = - velocity.y * Constants.BALL_BOUNCE_ABSORPTION;
            position.y = 0;
        }

        // Todo - TESTING: ERASE THIS
        shapeRenderer.setColor(0,0,1,1);
        shapeRenderer.rect(0,0,viewport.getScreenWidth(), Constants.BALL_JUMP_ERROR);

        shapeRenderer.setColor(0.5f, 0, 0, 1);
        shapeRenderer.circle(position.x, position.y + Constants.BALL_RADIUS, Constants.BALL_RADIUS, 50);
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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (position.y < Constants.BALL_JUMP_ERROR) {
            velocity.y = Constants.BALL_JUMP_SPEED;
            isJumping = true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (isJumping) {
            velocity.y = 0;
            isJumping = false;
        }
        return false;
    }
}
