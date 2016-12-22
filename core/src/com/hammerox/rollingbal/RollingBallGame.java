package com.hammerox.rollingbal;

import com.badlogic.gdx.Game;

public class RollingBallGame extends Game {

    InitialScreen initialScreen;

	@Override
	public void create () {
        initialScreen = new InitialScreen();

        setScreen(initialScreen);
    }

}
