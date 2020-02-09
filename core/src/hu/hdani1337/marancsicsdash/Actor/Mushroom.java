package hu.hdani1337.marancsicsdash.Actor;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Mushroom extends OneSpriteStaticActor {

    public static final String MUSHROOM_TEXTURE = "pic/mushroom.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(MUSHROOM_TEXTURE);
    }

    public Mushroom(MyGame game) {
        super(game, MUSHROOM_TEXTURE);
        setSize(getWidth()*0.003f, getHeight()*0.003f);
    }
}
