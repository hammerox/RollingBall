package com.hammerox.rollingbal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static com.hammerox.rollingbal.Constants.*;
import static com.hammerox.rollingbal.Util.removeImprecision;

/**
 * Created by Mauricio on 22-Dec-16.
 */

public class Character extends InputAdapter {

    private Viewport viewport;

    private Vector2 position;
    private Vector2 velocity;

    private Vector2 lastPosition;
    private Vector2 lastVelocity;

    private boolean isLanded = false;
    private boolean isJumping = false;


    public Character(Viewport viewport) {
        this.viewport = viewport;

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
        if (position.y - BALL_RADIUS < 0) {
            land(0);
        }
    }


    public void render(ShapeRenderer shapeRenderer) {
        // FINISH UPDATING
        lastPosition.set(position);
        lastVelocity.set(velocity);

        // RENDER
        // Todo - ERASE THIS: View jump zone
        shapeRenderer.setColor(0,0,1,1);
        shapeRenderer.rect(0,0,viewport.getScreenWidth(), BALL_JUMP_ERROR);

        shapeRenderer.setColor(0.5f, 0, 0, 1);
        shapeRenderer.circle(position.x, position.y, BALL_RADIUS, 50);
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
        if (isLanded) {
            velocity.y = BALL_JUMP_SPEED;
            isJumping = true;
            isLanded = false;
        }
    }

    public void stopJump() {
        if (isJumping) {
            velocity.y = 0;
            isJumping = false;
        }
    }

    public void land(float y) {
        velocity.y = - velocity.y * BALL_BOUNCE_ABSORPTION;
        position.y = y + BALL_RADIUS;
        isLanded = true;
    }

    public boolean landedOnPlatform(Platform platform) {
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.FLOOR);

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
        // TODO - BUG: After landing on platform and falling, ball can jump on midair.

        return false;
    }


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
}
