package com.hammerox.rollingbal.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static com.hammerox.rollingbal.Constants.*;

/**
 * Created by Mauricio on 23-Dec-16.
 */

public class Platform {

    public Vector2 position;
    public Vector2 size;
    public float top;

    public boolean isDeadly = false;


    public Platform(Vector2 position) {
        this.position = position;
        this.size = PLAT_SIZE_DEFAULT;
        top = position.y + size.y;
    }

    public Platform(Vector2 position, Vector2 size) {
        this.position = position;
        this.size = size;
        top = position.y + size.y;
    }

    public Platform(Vector2 position, Vector2 size, boolean isDeadly) {
        this(position, size);
        this.isDeadly = isDeadly;
    }


    public void render(ShapeRenderer shapeRenderer) {
        if (isDeadly) {
            shapeRenderer.setColor(Color.RED);
        } else {
            shapeRenderer.setColor(Color.BLUE);
        }

        shapeRenderer.rect(position.x, position.y, size.x, size.y);
    }

    public static Platform newRandomPlatform(float x0, float y0, float x1, float y1) {
        Vector2 position = new Vector2();
        position.x = MathUtils.random() * (x1 - x0) + x0;
        position.y = MathUtils.random() * (y1 - y0) + y0;
        return new Platform(position);
    }
}