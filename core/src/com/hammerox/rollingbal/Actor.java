package com.hammerox.rollingbal;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Mauricio on 10-Jan-17.
 */

public abstract class Actor {

    Vector2 position;
    Vector2 velocity;
    Vector2 lastPosition;
    Vector2 lastVelocity;
    Vector2 size;

    public abstract void renderShape(ShapeRenderer shapeRenderer);
    public abstract void move(float delta);

}
