package com.hammerox.rollingbal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by Mauricio on 22-Dec-16.
 */

public class InitialScreen extends ScreenAdapter {

    public final static String TAG = InitialScreen.class.getName();

    FitViewport viewport;
    ShapeRenderer shapeRenderer;

    Ball ball;

    float WORLD_SIZE = 100.0f;

    @Override
    public void show() {
        Gdx.app.log(TAG, "show");

        shapeRenderer = new ShapeRenderer();
        viewport = new FitViewport(WORLD_SIZE, WORLD_SIZE);

        ball = new Ball(viewport);
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log(TAG, "resize");
        viewport.update(width, height, true);

        ball.init();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        ball.render(delta, shapeRenderer);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
