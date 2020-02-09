package hu.hdani1337.marancsicsdash.Actor;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteAnimatedActor;

public class Coin extends OneSpriteAnimatedActor {

    public static final String COIN_ATLAS = "atlas/coin.atlas";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTextureAtlas(COIN_ATLAS);
    }

    public Coin(MyGame game) {
        super(game, COIN_ATLAS);
        setFps(60);
        setSize(getWidth()*0.008f, getHeight()*0.008f);
    }
}
