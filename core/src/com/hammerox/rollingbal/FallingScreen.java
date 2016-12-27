package com.hammerox.rollingbal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.LinkedList;
import java.util.List;

import static com.hammerox.rollingbal.Constants.*;

/**
 * Created by Mauricio on 27-Dec-16.
 */

public class FallingScreen extends ScreenAdapter {

    public final static String TAG = FallingScreen.class.getName();

    private ExtendViewport viewport;
    private ShapeRenderer shapeRenderer;

    private Character character;
    private List<Platform> allPlatforms;

    @Override
    public void show() {
        Gdx.app.log(TAG, "show");

        shapeRenderer = new ShapeRenderer();
        viewport = new ExtendViewport(WORLD_SIZE, WORLD_SIZE);

        character = new Character(viewport);
        character.init(WORLD_SIZE/2, WORLD_SIZE/2);

        allPlatforms = new LinkedList<Platform>();
        float add = WORLD_SIZE / 5.0f;
        float bottom = 0;
        for (int i = 0; i < 5; i++) {
            allPlatforms.add(Platform.newRandomPlatform(0, bottom, WORLD_SIZE, bottom + add));
            bottom += add;
        }
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "resize");
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        // TODO - Uncomment delta to debug
        delta = 1.0f / 60.0f;

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        // UPDATE
            // Update player
        character.update(delta);

            // Update camera
        viewport.getCamera().position.y =
                (character.getPosition().y > (0.5f - CAMERA_BALL_OFFSET) * WORLD_SIZE)
                        ? character.getPosition().y + CAMERA_BALL_OFFSET * WORLD_SIZE
                        : WORLD_SIZE / 2;

            // Player-Platform collisions
        for (Platform platform : allPlatforms) {
            character.landedOnPlatform(platform);
        }

        // RENDER
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.CYAN);
        shapeRenderer.rect(0,0,WORLD_SIZE, WORLD_SIZE);
        for (Platform platform : allPlatforms) {
            platform.render(shapeRenderer);
        }
        character.render(shapeRenderer);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        Gdx.app.log(TAG, "dispose");
        shapeRenderer.dispose();
    }
}
