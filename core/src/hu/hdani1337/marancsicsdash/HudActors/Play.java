package hu.hdani1337.marancsicsdash.HudActors;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Play extends OneSpriteStaticActor {

    public static final String PLAY_TEXTURE = "pic/play.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(PLAY_TEXTURE);
    }

    public Play(MyGame game) {
        super(game, PLAY_TEXTURE);
    }
}
