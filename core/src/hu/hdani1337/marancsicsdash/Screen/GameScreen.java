package hu.hdani1337.marancsicsdash.Screen;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyScreen;
import hu.hdani1337.marancsicsdash.Stage.GameStage;
import hu.hdani1337.marancsicsdash.Stage.HudStage;

public class GameScreen extends MyScreen {

    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(GameStage.class, assetList);
        assetList.collectAssetDescriptor(HudStage.class, assetList);
    }

    public GameScreen(MyGame game) {
        super(game);
    }

    public GameStage gameStage;

    @Override
    protected void afterAssetsLoaded() {
        gameStage = new GameStage(game);
        addStage(gameStage,1,false);
        addStage(new HudStage(game, gameStage),2, true);
    }

    @Override
    public AssetList getAssetList() {
        return assetList;
    }

    @Override
    public void init() {

    }
}
