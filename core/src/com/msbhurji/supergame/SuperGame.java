package com.msbhurji.supergame;

import com.badlogic.gdx.Game;
import com.msbhurji.supergame.screens.MainGame;

public class SuperGame extends Game {
    public static final String TITLE = "Super Game";
    public static final int V_WIDTH = 640;
    public static final int V_HEIGHT = 360;
    public static final int SCALE = 2;
    public static final boolean DEBUG = false;

	@Override
	public void create() {
		setScreen(new MainGame());
	}

    @Override
    public void dispose() {
    }

}
