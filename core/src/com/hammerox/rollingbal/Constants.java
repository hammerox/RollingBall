package com.hammerox.rollingbal;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Mauricio on 22-Dec-16.
 */

public class Constants {

    // World constants
    public final static float   WORLD_SIZE = 100.0f;
    public final static Vector2 WORLD_GRAVITY = new Vector2(0, -250.0f);
    public final static float   CAMERA_BALL_OFFSET = 0.1f;

    // Ball constants
    public final static float   BALL_RADIUS = 3.0f;
    public final static Vector2 BALL_COLLISION_VECTOR = new Vector2(0, - BALL_RADIUS);
    public final static float   BALL_INPUT_VELOCITY = 50.0f;
    public final static float   BALL_JUMP_SPEED = 150.0f;
    public final static float   BALL_JUMP_ERROR = 0.1f;
    public final static float   ACCELEROMETER_FACTOR = 3000.0f;
    public final static float   BALL_BOUNCE_ABSORPTION = 0f;    // Between 0 and 1

    // Screen chasing constants
    public final static float   CAMERA_SPEED = 40.0f;
    public final static float   CAMERA_LIMIT_RATIO = 0.3f;    // Between 0 and 0.5

    // Platform constants
    public final static Vector2 PLAT_SIZE_DEFAULT = new Vector2(15.0f, 3.0f);

    // Obstacle constants
    public final static float   OBSTACLE_DISTANCE = WORLD_SIZE / 3;

    // Font constants
    public final static String  FONT_ROBOTO_PATH = "Roboto-Bold.ttf";
    public final static int     SCORE_FONT_SIZE = 22;
}
