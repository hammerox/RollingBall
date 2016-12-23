package com.hammerox.rollingbal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.hammerox.rollingbal.Constants.*;

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

    public void init(float x, float y) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
    }

    public void render(float delta, ShapeRenderer shapeRenderer) {

        // INPUT RESPONSE
            // Accelerometer
        float accelerometer = Gdx.input.getAccelerometerY();
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
            // Jump status
        if (isJumping && velocity.y <= 0) isJumping = false;

            // Position
        velocity.mulAdd(WORLD_GRAVITY, delta);
        position.mulAdd(velocity, delta);

        // COLLISIONS
            // With walls
        if (position.x - BALL_RADIUS < 0) {
            position.x = BALL_RADIUS;
        } else if (position.x + BALL_RADIUS > viewport.getWorldWidth()) {
            position.x = viewport.getWorldWidth() - BALL_RADIUS;
        }

            // With ground
        if (position.y < 0) {
            velocity.y = - velocity.y * BALL_BOUNCE_ABSORPTION;
            position.y = 0;
        }

        // Todo - ERASE THIS: View jump zone
        shapeRenderer.setColor(0,0,1,1);
        shapeRenderer.rect(0,0,viewport.getScreenWidth(), BALL_JUMP_ERROR);

        shapeRenderer.setColor(0.5f, 0, 0, 1);
        shapeRenderer.circle(position.x, position.y + BALL_RADIUS, BALL_RADIUS, 50);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        jump();
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        stopJump();
        return false;
    }


    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.SPACE:
                jump();
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.D:
                velocity.x = 0;
                break;
            case Input.Keys.SPACE:
                stopJump();
                break;
        }
        return false;
    }


    public void jump() {
        if (position.y < BALL_JUMP_ERROR) {
            velocity.y = BALL_JUMP_SPEED;
            isJumping = true;
        }
    }

    public void stopJump() {
        if (isJumping) {
            velocity.y = 0;
            isJumping = false;
        }
    }
}
