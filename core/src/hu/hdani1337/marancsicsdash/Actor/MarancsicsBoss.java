package hu.hdani1337.marancsicsdash.Actor;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteAnimatedActor;

public class MarancsicsBoss extends OneSpriteAnimatedActor {

    public static final String MARANCSICSBOSS_ATLAS = "atlas/marancsicsBoss.atlas";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTextureAtlas(MARANCSICSBOSS_ATLAS);
    }

    public MarancsicsBoss(MyGame game) {
        super(game, MARANCSICSBOSS_ATLAS);
        setFps(15);
        setSize(getWidth()*0.011f, getHeight()*0.011f);
    }
}
