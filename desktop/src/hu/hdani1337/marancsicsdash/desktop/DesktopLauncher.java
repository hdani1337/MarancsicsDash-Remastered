package hu.hdani1337.marancsicsdash.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import hu.hdani1337.marancsicsdash.MarancsicsDash;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Marancsics Dash";
		config.addIcon("ic_launcher-web.png", Files.FileType.Internal);
		new LwjglApplication(new MarancsicsDash(false), config);
		config.width = 1;
		config.height = 1;
	}
}
