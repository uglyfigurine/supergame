package com.msbhurji.supergame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.msbhurji.supergame.SuperGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = SuperGame.TITLE;
        config.width = SuperGame.V_WIDTH * SuperGame.SCALE;
        config.height = SuperGame.V_HEIGHT * SuperGame.SCALE;
//        config.resizable = false;
        config.vSyncEnabled = true;
//      Try this later >_<
//		config.useGL30 = true;
		new LwjglApplication(new SuperGame(), config);
	}
}
