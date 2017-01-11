package com.hammerox.rollingbal;

import com.badlogic.gdx.math.MathUtils;
import com.hammerox.rollingbal.actors.Actor;
import com.hammerox.rollingbal.actors.DoublePlatform;

import static com.hammerox.rollingbal.Constants.*;

/**
 * Created by Mauricio on 11-Jan-17.
 */

public class ObstacleFactory {

    public static Actor create(Level level, float y) {
        switch (level) {

            case SPIKES:
                return (MathUtils.random() > SPIKE_CREATION_CHANCE)
                        ? DoublePlatform.create(y, false)
                        : DoublePlatform.create(y, true);

            default:
                return DoublePlatform.create(y, false);
        }
    }


}
