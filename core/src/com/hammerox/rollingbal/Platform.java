package com.hammerox.rollingbal;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import static com.hammerox.rollingbal.Constants.*;

/**
 * Created by Mauricio on 23-Dec-16.
 */

public class Platform {

    public Vector2 position;
    public Vector2 size;
    public float top;

    public Platform(Vector2 position) {
        this.position = position;
        this.size = PLAT_SIZE_DEFAULT;
        top = position.y + size.y;
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(position.x, position.y, size.x, size.y);
    }
}
