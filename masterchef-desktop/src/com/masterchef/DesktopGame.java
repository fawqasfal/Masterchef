package com.masterchef;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopGame {
	public static void main(String args[]) {
		new LwjglApplication(new Game(), "Masterchef", 800, 800, false);
	}
}
