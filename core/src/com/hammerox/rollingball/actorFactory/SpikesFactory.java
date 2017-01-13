package com.hammerox.rollingball.actorFactory;

import com.badlogic.gdx.math.MathUtils;
import com.hammerox.rollingball.Obstacles;
import com.hammerox.rollingball.actors.Character;
import com.hammerox.rollingball.actors.DoublePlatform;

import static com.hammerox.rollingball.Constants.*;

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
