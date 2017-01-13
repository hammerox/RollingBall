package com.hammerox.rollingbal;


import com.hammerox.rollingbal.actorFactory.ActorFactory;
import com.hammerox.rollingbal.actorFactory.CasualFactory;
import com.hammerox.rollingbal.actorFactory.SpeedFactory;
import com.hammerox.rollingbal.actorFactory.SpikesFactory;

import static com.hammerox.rollingbal.Constants.*;

/**
 * Created by Mauricio on 11-Jan-17.
 */

public enum Level {
    CASUAL  (CAMERA_SPEED_SLOW, false, new CasualFactory()),
    SPEED   (CAMERA_SPEED_FAST, false, new SpeedFactory()),
    SPIKES  (CAMERA_SPEED_SLOW, true, new SpikesFactory());

    float gameSpeed;
    boolean gameCanKill;
    ActorFactory factory;

    Level(float gameSpeed, boolean gameCanKill, ActorFactory factory) {
        this.gameSpeed = gameSpeed;
        this.gameCanKill = gameCanKill;
        this.factory = factory;
    }
}