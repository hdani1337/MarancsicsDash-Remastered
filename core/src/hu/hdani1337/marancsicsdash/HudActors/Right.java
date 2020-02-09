package hu.hdani1337.marancsicsdash.HudActors;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Right extends OneSpriteStaticActor {

    public static final String RIGHT_TEXTURE = "pic/right.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(RIGHT_TEXTURE);
    }
    public Right(MyGame game) {
        super(game, RIGHT_TEXTURE);
    }
}
