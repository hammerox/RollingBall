package com.hammerox.rollingball.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hammerox.rollingball.RollingBallGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = 650;
        config.width = 400;
		new LwjglApplication(new RollingBallGame(), config);
	}
}
