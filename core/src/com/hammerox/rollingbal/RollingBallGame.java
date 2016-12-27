package com.hammerox.rollingbal;

import com.badlogic.gdx.Game;

public class RollingBallGame extends Game {

    TestScreen testScreen;
    FallingScreen fallingScreen;

	@Override
	public void create () {
        testScreen = new TestScreen();
        fallingScreen = new FallingScreen();

        setScreen(fallingScreen);
    }

}
