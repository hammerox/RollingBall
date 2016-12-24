package com.hammerox.rollingbal;

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

    public Platform(Vector2 position) {
        this.position = position;
        this.size = PLAT_SIZE_DEFAULT;
        top = position.y + size.y;
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(position.x, position.y, size.x, size.y);
    }

    public static Platform newRandomPlatform(float x0, float y0, float x1, float y1) {
        Vector2 position = new Vector2();
        position.x = MathUtils.random() * (x1 - x0);
        position.y = MathUtils.random() * (y1 - y0);
        return new Platform(position);
    }
}
