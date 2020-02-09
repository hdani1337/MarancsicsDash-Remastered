package hu.hdani1337.marancsicsdash;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.hdani1337.marancsicsdash.Screen.GameScreen;
import hu.hdani1337.marancsicsdash.Screen.MenuScreen;
import hu.hdani1337.marancsicsdash.Stage.LoadingStage;

public class MarancsicsDash extends MyGame {

	public MarancsicsDash() {
	}

	public MarancsicsDash(boolean debug) {
		super(debug);
	}

	@Override
	public void create() {
		super.create();
		setLoadingStage(new LoadingStage(this));
		setScreen(new MenuScreen(this));
	}
}
