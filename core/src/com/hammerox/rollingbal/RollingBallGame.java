package com.hammerox.rollingbal;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class RollingBallGame extends Game {

    TestScreen testScreen;
    FallingScreen fallingScreen;
    LevelScreen levelScreen;

	@Override
	public void create () {
        testScreen = new TestScreen();
        fallingScreen = new FallingScreen();
        levelScreen = new LevelScreen();

        switch (Gdx.app.getType()) {
            default:
                setScreen(fallingScreen);
                break;
        }

    }

}
