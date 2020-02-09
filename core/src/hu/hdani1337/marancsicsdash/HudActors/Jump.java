package hu.hdani1337.marancsicsdash.HudActors;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class Jump extends OneSpriteStaticActor {

    public static final String JUMP_TEXTURE = "pic/jump.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(JUMP_TEXTURE);
    }

    public Jump(MyGame game) {
        super(game, JUMP_TEXTURE);
    }
}
