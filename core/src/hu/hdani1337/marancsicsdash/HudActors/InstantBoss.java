package hu.hdani1337.marancsicsdash.HudActors;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;

public class InstantBoss extends OneSpriteStaticActor {

    public static final String INSTANTBOSS_TEXTURE = "pic/instantBoss.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(INSTANTBOSS_TEXTURE);
    }

    public InstantBoss(MyGame game) {
        super(game, INSTANTBOSS_TEXTURE);
    }
}
