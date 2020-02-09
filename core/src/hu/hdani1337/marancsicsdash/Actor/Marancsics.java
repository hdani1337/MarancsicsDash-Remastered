package hu.hdani1337.marancsicsdash.Actor;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteAnimatedActor;

public class Marancsics extends OneSpriteAnimatedActor {

    public static final String MARANCSICS_ATLAS = "atlas/marancsics.atlas";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTextureAtlas(MARANCSICS_ATLAS);
    }

    public Marancsics(MyGame game) {
        super(game, MARANCSICS_ATLAS);
        setFps(12);
        setSize(getWidth()*0.011f, getHeight()*0.011f);
    }
}
