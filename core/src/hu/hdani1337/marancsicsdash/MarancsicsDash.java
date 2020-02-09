package hu.hdani1337.marancsicsdash;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.hdani1337.marancsicsdash.Screen.IntroScreen;
import hu.hdani1337.marancsicsdash.Stage.LoadingStage;

public class MarancsicsDash extends MyGame {

	public MarancsicsDash() {
	}

	public MarancsicsDash(boolean debug) {
		super(debug);
	}

	public static boolean needsLoading;

	@Override
	public void create() {
		super.create();
		needsLoading = false;
		setLoadingStage(new LoadingStage(this));
		setScreen(new IntroScreen(this));
	}
}
