package com.hammerox.rollingbal;

import com.badlogic.gdx.Game;

public class RollingBallGame extends Game {

    TestScreen testScreen;

	@Override
	public void create () {
        testScreen = new TestScreen();

        setScreen(testScreen);
    }

}
