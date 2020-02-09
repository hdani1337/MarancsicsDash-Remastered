package hu.hdani1337.marancsicsdash.Stage;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteAnimatedActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.hdani1337.marancsicsdash.Actor.Marancsics;
import hu.hdani1337.marancsicsdash.Actor.Zsolti;

public class LoadingStage extends hu.csanyzeg.master.MyBaseClasses.Assets.LoadingStage {

    public static AssetList assetList;
    static
    {
        assetList = new AssetList();
        assetList.addTextureAtlas(Marancsics.MARANCSICS_ATLAS).protect = true;
        assetList.addTextureAtlas(Zsolti.ZSOLTI_ATLAS).protect = true;
    }

    public LoadingStage(MyGame game) {
        super(new ResponseViewport(900), game);
        addActor(new OneSpriteAnimatedActor(game, (Math.random() < 0.5f) ? Marancsics.MARANCSICS_ATLAS : Zsolti.ZSOLTI_ATLAS){
            @Override
            public void init() {
                super.init();
                setFps(18);
                setPosition(getViewport().getWorldWidth() / 2 - this.getWidth() / 2, getViewport().getWorldHeight() / 2 - this.getHeight() / 2);
            }

            @Override
            public void act(float delta) {
                super.act(delta);
                setRotation(getRotation()-5);
            }
        });
    }

    @Override
    public AssetList getAssetList() {
        return assetList;
    }
}
