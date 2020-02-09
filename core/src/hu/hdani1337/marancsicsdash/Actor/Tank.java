package hu.hdani1337.marancsicsdash.Actor;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteAnimatedActor;

public class Tank extends OneSpriteAnimatedActor {

    public static final String TANK_ATLAS = "atlas/tank.atlas";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTextureAtlas(TANK_ATLAS);
    }

    public Tank(MyGame game) {
        super(game, TANK_ATLAS);
        setFps(15);
        setSize(getWidth()*0.007f, getHeight()*0.007f);
    }
}
