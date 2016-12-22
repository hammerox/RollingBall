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

    public Ball(Viewport viewport) {
        this.viewport = viewport;

        Gdx.input.setInputProcessor(this);
    }

    public void init() {
        position = new Vector2(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2);
        velocity = new Vector2(0, 0);
    }

    public void render(float delta, ShapeRenderer shapeRenderer) {

        // Input response
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

        // Position update
        velocity.mulAdd(Constants.WORLD_GRAVITY, delta);
        position.mulAdd(velocity, delta);

        // Collision with walls
        if (position.x - Constants.BALL_RADIUS < 0) {
            position.x = Constants.BALL_RADIUS;
        } else if (position.x + Constants.BALL_RADIUS > viewport.getWorldWidth()) {
            position.x = viewport.getWorldWidth() - Constants.BALL_RADIUS;
        }

        // Collision with ground
        if (position.y < 0) {
            velocity.y = - velocity.y / 1.2f;
            position.y = 0;
        }

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
        velocity.y += Constants.BALL_JUMP_SPEED;
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
