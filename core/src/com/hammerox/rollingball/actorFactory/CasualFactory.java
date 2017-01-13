package com.hammerox.rollingball.actorFactory;

import com.hammerox.rollingball.Obstacles;
import com.hammerox.rollingball.actors.Character;
import com.hammerox.rollingball.actors.DoublePlatform;

/**
 * Created by Mauricio on 12-Jan-17.
 */

public class CasualFactory extends ActorFactory {

    @Override
    public Character addCharacter() {
        return new Character();
    }

    @Override
    public void addObstacle(Obstacles obstacleList, float height) {
        DoublePlatform doublePlatform = createRandomDoublePlatform(height, false);
        obstacleList.add(doublePlatform);
    }
}
