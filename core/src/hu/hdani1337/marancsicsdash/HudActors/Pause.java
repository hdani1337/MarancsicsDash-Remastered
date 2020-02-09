package hu.hdani1337.marancsicsdash.HudActors;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Pause extends OneSpriteStaticActor {

    public static final String PAUSE_TEXTURE = "pic/pause.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(PAUSE_TEXTURE);
    }

    public Pause(MyGame game) {
        super(game, PAUSE_TEXTURE);
    }
}
