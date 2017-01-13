package com.hammerox.rollingball;


import com.hammerox.rollingball.actorFactory.ActorFactory;
import com.hammerox.rollingball.actorFactory.CasualFactory;
import com.hammerox.rollingball.actorFactory.SpeedFactory;
import com.hammerox.rollingball.actorFactory.SpikesFactory;

import static com.hammerox.rollingball.Constants.*;

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