package com.hammerox.rollingbal.actorFactory;

import com.badlogic.gdx.math.MathUtils;
import com.hammerox.rollingbal.Obstacles;
import com.hammerox.rollingbal.actors.Character;
import com.hammerox.rollingbal.actors.DoublePlatform;

import static com.hammerox.rollingbal.Constants.*;

/**
 * Created by Mauricio on 12-Jan-17.
 */

public class SpikesFactory extends ActorFactory {

    @Override
    public Character addCharacter() {
        return new Character();
    }

    @Override
    public void addObstacle(Obstacles obstacleList, float height) {
        DoublePlatform doublePlatform = (MathUtils.random() > SPIKE_CREATION_CHANCE)
                ? createRandomDoublePlatform(height, false)
                : createRandomDoublePlatform(height, true);
        obstacleList.add(doublePlatform);
    }
}
