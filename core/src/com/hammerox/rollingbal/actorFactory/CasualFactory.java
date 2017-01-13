package com.hammerox.rollingbal.actorFactory;

import com.hammerox.rollingbal.Obstacles;
import com.hammerox.rollingbal.actors.Character;
import com.hammerox.rollingbal.actors.DoublePlatform;

/**
 * Created by Mauricio on 12-Jan-17.
 */

public class CasualFactory extends ActorFactory {

    @Override
    public Character addCharacter() {
        return new Character();
    }

    @Override
    public void addObstacle(Obstacles obstacleList) {
        float height = obstacleList.getLastObstacleHeight();
        DoublePlatform doublePlatform = createRandomDoublePlatform(height, false);
        obstacleList.add(doublePlatform);
    }
}
