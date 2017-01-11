package com.hammerox.rollingbal;


import static com.hammerox.rollingbal.Constants.*;

/**
 * Created by Mauricio on 11-Jan-17.
 */

public enum Level {
    CASUAL  (CAMERA_SPEED_SLOW, false),
    SPEED   (CAMERA_SPEED_FAST, false),
    SPIKES  (CAMERA_SPEED_SLOW, true);

    float gameSpeed;
    boolean gameCanKill;

    Level(float gameSpeed, boolean gameCanKill) {
        this.gameSpeed = gameSpeed;
        this.gameCanKill = gameCanKill;
    }
}