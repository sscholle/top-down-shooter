
package com.badlogic.gradletest;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Pigeon Masher";
        config.useGL20 = true;
        config.width = 720;
        config.height = 1200;

		new LwjglApplication(new HelloApp(), config);
	}
}
