package com.hammerox.rollingball.actors;

/**
 * Created by Mauricio on 10-Jan-17.
 */

public interface CollisionListener {
    boolean checkCollision(Actor subject);
    void onCollision(Actor subject);
}
