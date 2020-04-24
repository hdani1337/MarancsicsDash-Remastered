package hu.hdani1337.marancsicsdash.HudActors;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Left extends OneSpriteStaticActor {

    public static final String LEFT_TEXTURE = "pic/ui/left.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(LEFT_TEXTURE);
    }

    public Left(MyGame game) {
        super(game, LEFT_TEXTURE);
    }
}
