package hu.hdani1337.marancsicsdash.Screen;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;
import hu.hdani1337.marancsicsdash.Stage.GameOverStage;
import hu.hdani1337.marancsicsdash.Stage.GameStage;
import hu.hdani1337.marancsicsdash.Stage.HudStage;
import hu.hdani1337.marancsicsdash.Stage.PauseStage;

public class GameScreen extends MyScreen {
    //region AssetList
    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(GameStage.class, assetList);
        assetList.collectAssetDescriptor(HudStage.class, assetList);
        assetList.collectAssetDescriptor(GameOverStage.class, assetList);
    }
    //endregion
    //region Konstruktor
    public GameScreen(MyGame game) {
        super(game);
    }
    //endregion
    //region Változók
    public GameStage gameStage;
    //endregion
    //region Absztrakt metódusok
    @Override
    protected void afterAssetsLoaded() {
        gameStage = new GameStage(game);
        HudStage.stage = gameStage;
        addStage(gameStage,1,false);
        addStage(new HudStage(game),2, true);
        addStage(new PauseStage(game),3, true);
        addStage(new GameOverStage(game),4, true);
    }

    @Override
    public AssetList getAssetList() {
        return assetList;
    }

    @Override
    public void init() {

    }
    //endregion
}
