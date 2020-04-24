package hu.hdani1337.marancsicsdash.Screen;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;
import hu.hdani1337.marancsicsdash.Stage.BossStage;
import hu.hdani1337.marancsicsdash.Stage.GameOverStage;
import hu.hdani1337.marancsicsdash.Stage.HudStage;
import hu.hdani1337.marancsicsdash.Stage.PauseStage;
import hu.hdani1337.marancsicsdash.Stage.VictoryStage;

public class BossScreen extends MyScreen {

    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(BossStage.class, assetList);
    }

    public BossScreen(MyGame game) {
        super(game);
    }

    public BossStage bossStage;

    @Override
    protected void afterAssetsLoaded() {
        bossStage = new BossStage(game);
        HudStage.stage = bossStage;
        addStage(bossStage,1,false);
        addStage(new HudStage(game),2,true);
        addStage(new PauseStage(game),3, true);
        addStage(new GameOverStage(game),4, true);
        addStage(new VictoryStage(game),5, true);
    }

    @Override
    public AssetList getAssetList() {
        return assetList;
    }

    @Override
    public void init() {

    }
}
