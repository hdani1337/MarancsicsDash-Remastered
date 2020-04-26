package hu.hdani1337.marancsicsdash.Actor;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteAnimatedActor;

public class Fire extends OneSpriteAnimatedActor {
    //region AssetList
    public static final String FIRE_ATLAS = "atlas/fire.atlas";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTextureAtlas(FIRE_ATLAS);
    }
    //endregion
    //region Konstruktor
    public Fire(MyGame game) {
        super(game, FIRE_ATLAS);
        setFps(60);
        setSize(getWidth()*0.02f,getHeight()*0.02f);
    }
    //endregion
}
